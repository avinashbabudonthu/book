@echo off
REM Remove jar files script for Fire Server services
REM This script removes jar files from the EC2 server via SSH
REM Execute from PowerShell or CMD: .\remove-jar-files.bat

REM Configuration
set EC2_IP=12.34.56.78
set EC2_USER=ec2-user
set PEM_FILE=C:\path-to-pem-file\my-file.pem

echo [INFO] Starting jar file removal on EC2...
echo [INFO] EC2 Server: %EC2_IP%
echo.

if not exist "%PEM_FILE%" (
    echo [ERROR] PEM file not found: %PEM_FILE%
    exit /b 1
)

ssh -i "%PEM_FILE%" -o StrictHostKeyChecking=no %EC2_USER%@%EC2_IP% "sudo rm -f ~/opt/java-services/my-app.jar"

if errorlevel 1 (
    echo [ERROR] SSH or remove command failed
    exit /b 1
)

echo [INFO] Jar file removal completed.
pause