# Topic Naming Conventions
------
### General convention
```
<message type>.<dataset name>.<data name>.<data format>
```

### Message Type
* logging: For logging data (slf4j, syslog etc)
* queuing: For classic queuing use cases
* tracking: For tracking events such as user clicks, page views, ad views etc
* etl/db: For ETL and CDC (change data capture) use cases such as database feeds
* streaming: For intermediate topics created by streaming processing pipelines
* push: For data that is being pushed from offline (batch computation) environments into online environments
* user: For user specific data such as scratch and test topics

### Dataset name
* Similar to Database name in traditional RDBMS systems. It is used as category to group topics together

### Data name
* Similar to database Table in traditional RDBMS systems. It is fine to include further dotted notation if developers wish to impose their own hierarchy withtin dataset namespace

### Data format
* Like avro, json, text, protobuf, csv, log
------
# References
* https://cnr.sh/posts/2017-08-29-how-paint-bike-shed-kafka-topic-naming-conventions/