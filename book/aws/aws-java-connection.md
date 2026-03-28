# Connecting Java applications to AWS

Reference for **AWS SDK for Java 2.x** (`software.amazon.awssdk.*`). Version 1.x (`com.amazonaws.*`) is in maintenance mode; new projects should use 2.x.

**Official guides**

- [Developer guide (2.x)](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)
- [Default credentials provider chain](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials-chain.html)
- [Credentials providers (2.x)](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/credentials-providers.html)

---

## 1. Add the SDK (Maven)

Use the **AWS SDK BOM** so service module versions stay aligned:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>bom</artifactId>
      <version>2.x.x</version> <!-- use current BOM from docs / Maven Central -->
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
  </dependency>
</dependencies>
```

([Set up a project](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/setup-project.html))

---

## 2. Default credentials provider chain (recommended default)

If you **do not** set a credentials provider on the client builder, the SDK uses **`DefaultCredentialsProvider`**, which searches in a **fixed order** until credentials are found:

1. **Java system properties** — `aws.accessKeyId`, `aws.secretAccessKey`, optional `aws.sessionToken`
2. **Environment variables** — `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, optional `AWS_SESSION_TOKEN`
3. **Web identity token** — `AWS_ROLE_ARN`, `AWS_WEB_IDENTITY_TOKEN_FILE`, etc. (common on **EKS** with IRSA)
4. **Shared `credentials` and `config` files** — including **IAM Identity Center (SSO)** profiles and assumed-role profiles
5. **Container credentials** — `AWS_CONTAINER_CREDENTIALS_*` (**ECS**, some Kubernetes setups)
6. **EC2 instance profile** — IMDS (**IAM role attached to the instance**)

**Important:** In SDK **2.x**, **system properties are checked before environment variables**. That order was different in SDK 1.x.

Minimal client (uses the chain above):

```java
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

S3Client s3 = S3Client.builder()
    .region(Region.US_EAST_1)
    .build();
```

Explicit (same behavior, clearer intent):

```java
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

S3Client s3 = S3Client.builder()
    .region(Region.US_EAST_1)
    .credentialsProvider(DefaultCredentialsProvider.create())
    .build();
```

Optional: `DefaultCredentialsProvider.builder().profileName("my-profile").build()` for a specific profile when the chain reaches the profile step.

---

## 3. Environment variables

Set `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` (and `AWS_SESSION_TOKEN` if using temporary credentials). You **do not** need to copy them into Java system properties for the default chain; the **`EnvironmentVariableCredentialsProvider`** step handles env vars.

Avoid embedding secrets in source or JVM `-D` flags in production.

---

## 4. IAM roles and managed identity (no long-lived keys)

Prefer **short-lived credentials** from the environment:

| Where it runs | How credentials are usually provided |
|---------------|----------------------------------------|
| **EC2** | Instance profile → IMDS |
| **Lambda** | Execution role (env / container) |
| **ECS** | Task role → container credentials endpoint |
| **EKS** | IRSA (web identity token + role) |
| **GitHub Actions / CI** | OIDC to assume a role (no static keys in the repo) |

The same `S3Client.builder().region(...).build()` code typically works once the runtime supplies credentials.

---

## 5. Shared config and AWS CLI (`aws configure`)

`aws configure` and profiles in `~/.aws/credentials` and `~/.aws/config` are **not** a separate protocol—they feed the **profile** step of the default chain (and tools like IAM Identity Center SSO).

Use `AWS_PROFILE` or `DefaultCredentialsProvider.builder().profileName("...")` to select a non-default profile.

---

## 6. Static access keys in code (discouraged for production)

The SDK allows **explicit** static credentials for tests or legacy integrations. **Do not** commit real keys; prefer env, secrets manager, or roles.

```java
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

S3Client s3 = S3Client.builder()
    .region(Region.US_EAST_1)
    .credentialsProvider(StaticCredentialsProvider.create(credentials))
    .build();
```

For temporary keys (STS), use `AwsSessionCredentials` and `StaticCredentialsProvider` with access key, secret, and session token.

---

## 7. Amazon S3 presigned URLs

Presigning uses **`S3Presigner`**, not `S3Client.utilities().presign()` (that pattern does not match the current 2.x API). Close the presigner when done if you want to release threads/resources cleanly.

```java
import java.time.Duration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

try (S3Presigner presigner = S3Presigner.create()) {
    GetObjectRequest getObject = GetObjectRequest.builder()
        .bucket("my-bucket")
        .key("my-key")
        .build();

    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(15))
        .getObjectRequest(getObject)
        .build();

    PresignedGetObjectRequest presigned = presigner.presignGetObject(presignRequest);
    String url = presigned.url().toExternalForm();
}
```

([Pre-signed URLs in Java 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3-presign.html))

---

## 8. Summary

| Approach | Typical use |
|----------|-------------|
| **Default chain** (no explicit provider) | Local dev (profile/SSO), EC2, Lambda, ECS, EKS |
| **Environment variables** | CI secrets, containers |
| **Web identity / IRSA** | Kubernetes |
| **Static keys in code** | Avoid in production; tests only with fake keys |
| **Presigned URLs** | Time-limited S3 GET/PUT without sharing account keys |
| **AWS CLI / profiles** | Feeds the profile step; same SDK clients |

Keep the SDK and BOM versions current for security and API updates; see the [AWS SDK for Java 2.x changelog](https://github.com/aws/aws-sdk-java-v2/blob/master/CHANGELOG.md) and release notes on GitHub.

---

*Last reviewed against AWS documentation conventions (SDK for Java 2.x credential chain and S3 presigner usage).*
