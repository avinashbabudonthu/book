# Map Timestamp with Time Zone column
* Use `columnDefinition` attribute in `Column` annotation
```
@Column(name = "run_from", columnDefinition= "TIMESTAMP WITH TIME ZONE")
@NotNull
@Temporal(TemporalType.TIMESTAMP)
private Date runFrom;
```
* We can use `not null` also like below
```
@Column(name = "run_from", columnDefinition = "timestamp with time zone not null")
@NotNull
@Temporal(TemporalType.TIMESTAMP)
private Date runFrom;
```