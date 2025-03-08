# Java application connection URL
* Oracle non-RAC (Real Application Clusters) database
```
jdbc:oracle:thin:@[host]:[port]:[sid]
jdbc:oracle:thin:@myhost.us.example.com:1521:prod
jdbc:oracle:thin:@localhost:1521:prod
```
* Oracle non-RAC database - If `sid` is not there then use below
```
jdbc:oracle:thin:@localhost:1521
```
* Oracle RAC database
```
jdbc:oracle:thin:@//<host>[:<port>]/<service_name>
jdbc:oracle:thin:@//myhost.example.com:1521/my_service
```