@echo off
echo -------------------------------
call C:\github\book\book\maven\create-core--java-project.bat
echo -------------------------------
cd /d %artifactId%
echo -------------------------------
set currentDirectory=%cd%
echo Current Directory = %currentDirectory%
echo -------------------------------
echo Deleting pom.xml
del pom.xml
echo Deleted pom.xml
echo -------------------------------
echo Copying pom.xml
copy C:\github\book\book\maven\core-java-pom.xml %currentDirectory%
echo Copied pom.xml
ren core-java-pom.xml pom.xml
cd /d src/main
mkdir resources
cd..
cd..
copy C:\github\book\book\maven\log4j.xml %currentDirectory%\src\main\resources
copy C:\github\book\book\maven\.gitignore %currentDirectory%
echo -------------------------------
echo Completed. Update groupId, artifactId, verion in pom.xml
echo -------------------------------
pause