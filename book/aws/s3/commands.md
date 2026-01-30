# S3 Commands
---
* Check bucker list
```
aws s3 ls
```
* Check files in bucket. This gives files and folders from bucket level
```
aws s3 ls s3://my-s3-bucket
```
* Check all files in all folders recursively
```
aws s3 ls s3://my-s3-bucket --recursive
```