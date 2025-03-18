@echo off
echo -------------------------------
set /p groupId=Enter group id:
set /p artifactId=Enter artifact id:
set /p version=Enter version:
mvn archetype:generate -DgroupId=%groupId% -DartifactId=%artifactId% -Dversion=%version% -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false