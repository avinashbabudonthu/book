# Windows commands
------
### Get current directory from batch file
```
%~dp0
```
* Set using above command like below
```
set current_directory=%~dp0
```
* Use current_direcoty like below:
```
cd /d %current_directory%
```
------
* Check windows version from cmd
```
winver
```
* user home directory
```
echo %userprofile%
```