# Allow password based connection from Java
------
* Find `pg_hba.conf` file in your PostgreSQL installation directory and add the following line. Check in location `C:\Program Files\PostgreSQL\17\data`
```
# IPv4 local connections:
host    all         all         127.0.0.1/32          md5
```
* You can start/restart the postgres server in case it is not running using the following command (or) you can also start from services
```
[root@host]# service postgresql restart
Stopping postgresql service:                               [  OK  ]
Starting postgresql service:                               [  OK  ]
```
