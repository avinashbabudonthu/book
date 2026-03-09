@echo off
REM Copy jar files script for Fire Server services
REM This script copies jar files from local machine to EC2 server
REM Execute from PowerShell or CMD: .\copy-jar-files.bat

REM Configuration
set EC2_IP=12.34.56.78
set EC2_USER=ec2-user
set PEM_FILE=C:\path-to-pem-file\my-file.pem
set REMOTE_DIR=~/opt/java-services
set LOCAL_BASE_DIR=C:\path-to-my-java-jar

echo [INFO] Starting jar file copy process...
echo [INFO] EC2 Server: %EC2_IP%
echo [INFO] Remote Directory: %REMOTE_DIR%
echo [INFO] Local Base Directory: %LOCAL_BASE_DIR%
echo.

REM Check if PEM file exists
if not exist "%PEM_FILE%" (
    echo [ERROR] PEM file not found: %PEM_FILE%
    exit /b 1
)
echo [INFO] PEM file found: %PEM_FILE%
echo.

REM Copy my-app.jar
set SERVICE=my-app
set LOCAL_JAR=%LOCAL_BASE_DIR%\%SERVICE%.jar
if exist "%LOCAL_JAR%" (
    echo [INFO] Copying %SERVICE%.jar...
    scp -i "%PEM_FILE%" -o StrictHostKeyChecking=no "%LOCAL_JAR%" %EC2_USER%@%EC2_IP%:%REMOTE_DIR%/
    if errorlevel 1 (
        echo [ERROR] Failed to copy %SERVICE%.jar
        exit /b 1
    ) else (
        echo [INFO] %SERVICE%.jar copied successfully
    )
) else (
    echo [WARN] JAR file not found: %LOCAL_JAR% (skipping)
)
echo.

echo [INFO] All jar files copied successfully!
pause