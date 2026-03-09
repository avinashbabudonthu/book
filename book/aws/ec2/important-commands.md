# Important commands
* Check running services
```
ps xw
```
* Remove file
```
sudo rm nohup.out
```
* Fore remove file
```
sudo rm -rf logs/
```
* Check files in folder
```
ls -alh
ls -ltr
sudo ls -alh /opt/java-services/
ls -alh /opt/java-services/
ls -ltr /opt/java-services/
ls -alh /opt/java-services/service-discovery.jar
ls -alh /opt/java-services/gateway.jar
ls -alh /opt/java-services/person-profile.jar
ls -alh /opt/java-services/employee-profile.jar
ls -alh /opt/java-services/incidents.jar
ls -alh /opt/java-services/asset-management.jar
ls -alh /opt/java-services/file-service.jar
ls -alh /opt/java-services/leave-management.jar
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