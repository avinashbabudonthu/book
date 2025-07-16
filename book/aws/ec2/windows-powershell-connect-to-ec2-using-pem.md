# 🛠 AWS EC2 Instance Setup Guide

## 🔐 SSH Access to EC2 Instance

```bash
ssh-add "C:\MyPC\test\aws\test-file.pem"
ssh ec2-user@12.345.678.901
```

```
ssh-add "C:\MyPC\test\aws\test-file.pem"
ssh -i C:\MyPC\test\aws\test-file.pem ec2-user@3.84.194.20
```

### ⚠️ If You Encounter a Permission Error:

> **Error Message:**
> ```
> Bad permissions. Try removing permissions for user: BUILTIN\Users (S-1-5-32-545)...
> WARNING: UNPROTECTED PRIVATE KEY FILE!
> Permissions for 'C:\MyPC\test\aws\test-file.pem' are too open.
> ```

**Fix using PowerShell:**

```powershell
$path = "C:\MyPC\test\aws\test-file.pem"
icacls $path /inheritance:r
cmd /c "icacls `"$path`" /grant:r `"$env:USERNAME`:F`""
```

---

## ☕ Install Java (Amazon Corretto 17)

```bash
sudo yum update -y
sudo yum install java-17-amazon-corretto-devel -y
```

---

## 🐳 Install Docker

```bash
sudo yum install docker -y
sudo systemctl start docker
sudo systemctl enable docker
```

### Enable Docker for `ec2-user` (no `sudo` needed)

```bash
sudo usermod -aG docker ec2-user
```

Either log out and log back in, or run:

```bash
newgrp docker
```

### ✅ Verify Docker Installation

```bash
sudo docker version
sudo docker info
```

---

## 🔧 Install Docker Compose

```bash
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose version
```
