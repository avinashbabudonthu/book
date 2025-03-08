### Get Port number of application
```
@Autowired
private org.springframework.core.env.Environment environment;

String port = environment.getProperty("local.server.port");
```