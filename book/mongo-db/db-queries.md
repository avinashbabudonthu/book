# DB Commands
* Run mongodb by giving db path
```
mongod.exe --dbpath C:\mongodb\data\db
```
* Help
```
help
```
* Show dbs
```
show dbs
show databases
```
* Create db. Use specific db. There is no `create` command for database. `use` will command will create and switch to dbs. If we run `show dbs` then new db is not visible. New db will be visible after 1st document insertion to any collection that db
```
use <database-name>
use office
```
* Show collections
```
show collections
```
* Show users
```
show users
```
* Drop database
```
db.dropDatabase()
```
* Get distinct values of specific field
```
db.order.distinct("status")
db.order.distinct("sourceType")
```
* Get distinct by multiple fields
```
db.order.aggregate([
  {
    $group: {
      _id: { status: "$status", sourceType: "$sourceType" }
    }
  },
  {
    $project: {
      _id: 0,
      status: "$_id.status",
      sourceType: "$_id.sourceType"
    }
  }
])
```