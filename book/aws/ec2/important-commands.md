# Important commands
* Check running services
```
ps xw
```
* Remove file
```
sudo rm nohup.out
```
* Fore remove folder
```
sudo rm -rf logs/
```
* Check files in folder
```
ls -alh
ls -ltr
ls -alh /opt/java-services/
ls -ltr /opt/java-services/
sudo ls -alh /opt/java-services/
```
* Monito any file
```
tail -n 100 app.log
tail -f app.log
tail -f nohup.out
```
* Disk usage
```
df -h
```
* Force kill service
```
sudo kill -9 id
```