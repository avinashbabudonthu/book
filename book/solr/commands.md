# Apache Solr Commands
* Start solr. This run solr in backgroud
```
solr start
```
* Start solr in fore ground
```
solr start -f
```
* Start solr by specific port. Default port is `8983`
```
solr start -p 9000
```
* Access solr dashbaord - http://localhost:8983
* Solr status
```
solr status
```
* Stop solr and all ports
```
solr stop -all
```
* Restart solr
```
solr restart
```
* Help
```
solr help
```
* Create core
```
solr create -c core-1
```