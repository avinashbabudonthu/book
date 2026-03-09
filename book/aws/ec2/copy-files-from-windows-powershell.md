# Copy files from windows powershell
* Connect to ec2 instance
```
ssh -i "C:\path-to-pem-file\my-file.pem" ec2-user@12.34.56.78
```
* Copy files. `~/opt/java-services/` is path in EC2 instance
```
scp -i "C:\path-to-pem-file\my-file.pem" "C:\path-to-my-java-jar\my-app.jar" ec2-user@12.34.56.78:~/opt/java-services/
```
* If we need to use proxy jump while copy
```
scp -i C:\path-to-pem-file\my-file.pem -o ProxyJump=ec2-user@90.12.34.56 C:\path-to-my-java-jar\my-app.jar ec2-user@12.34.56.78:~/opt/java-services/
```