# Pre request script
------
* If API is as follows
```
http://localhost:9000/api/v1/students
```
* In folder - go to `Pre-request Script` tab. Define below properties
```
var httpProtocol = "http"
var localhost = "localhost"
var localPort = 9000

pm.request.url.protocol=httpProtocol
pm.request.url.host=localhost
pm.request.url.port=localPort
```
* Then we can directly API as /api/v1/students