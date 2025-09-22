# CSV Processor - Spring Batch Application

- A Spring Boot application that demonstrates batch processing capabilities by reading person data from a CSV file, transforming it, and storing it in a MySQL database.
- Spring creates necessary tables for job metadata management. These table scripts can be in
- The scripts are located in the package: `org.springframework.batch.core` within the JAR file `spring-batch-core-<version>.jar`
- File names follow the format: schema-<db>.sql where <db> is the short name of your database (Ex: mysql, postgresql, oracle, h2, etc.)

## Overview

This application showcases Spring Batch framework features including:
- CSV file reading and processing
- Data transformation (converting names to uppercase)
- Database persistence with JDBC batch operations
- Job monitoring and completion notifications
- REST API for manual job execution
- Scheduled task demonstrations

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Batch**
- **Spring Web**
- **Spring Actuator**
- **MySQL 8.x**
- **Lombok**
- **Maven**

## Project Structure

```
src/
├── main/
│   ├── java/com/itools/
│   │   ├── Main.java                              # Application entry point
│   │   ├── component/
│   │   │   ├── JobCompletionNotificationListener.java  # Job lifecycle listener
│   │   │   └── PersonItemProcessor.java           # Data transformation processor
│   │   ├── config/
│   │   │   └── AppConfiguration.java              # Batch job configuration
│   │   ├── controller/
│   │   │   └── JobController.java                 # REST endpoint for job execution
│   │   └── record/
│   │       └── Person.java                        # Data model
│   └── resources/
│       ├── application.properties                 # Application configuration
│       ├── person-data.csv                       # Sample input data
│       └── schema-all.sql                        # Database schema
```

## Features

### 1. Batch Processing Pipeline
- **Reader**: Reads CSV data from `person-data.csv` - [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java#L28)
- **Processor**: Transforms first and last names to uppercase - [PersonItemProcessor.java](src/main/java/com/itools/component/PersonItemProcessor.java#L12)
- **Writer**: Persists processed data to MySQL database - [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java#L45)

### 2. Job Configuration
- Chunk-based processing (chunk size: 3) - [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java#L55)
- Transaction management with MySQL - [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java#L55)
- Automatic job execution on startup - [application.properties](src/main/resources/application.properties#L15)
- Manual job triggering via REST API - [JobController.java](src/main/java/com/itools/controller/JobController.java#L17)

### 3. Monitoring & Notifications
- Job execution lifecycle logging - [JobCompletionNotificationListener.java](src/main/java/com/itools/component/JobCompletionNotificationListener.java#L18)
- Database query verification after job completion - [JobCompletionNotificationListener.java](src/main/java/com/itools/component/JobCompletionNotificationListener.java#L29)
- Spring Actuator endpoints for monitoring - [pom.xml](pom.xml#L37)

### 4. Scheduled Tasks
- Demo scheduled tasks running at 5-second and 10-second intervals - [Main.java](src/main/java/com/itools/Main.java#L18)
- Thread information logging - [Main.java](src/main/java/com/itools/Main.java#L18)

## Prerequisites

### Database Setup
1. Install MySQL 8.x
2. Create database and user:
```sql
CREATE DATABASE IF NOT EXISTS practice;
CREATE USER 'user1'@'localhost' IDENTIFIED BY 'Password22092025';
GRANT ALL ON practice.* TO 'user1'@'localhost';
```

### Java Environment
- Java 21 or higher
- Maven 3.6+

## Configuration

### Database Configuration
Update [application.properties](src/main/resources/application.properties) with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/practice?useSSL=false&verifyServerCertificate=false&noAccessToProcedureBodies=true
spring.datasource.username=user1
spring.datasource.password=Password22092025
```

### Batch Configuration
```properties
spring.batch.job.enabled=true
spring.batch.jdbc.initialize-schema=always
```

## Running the Application

### 1. Build the Project
```bash
mvn clean compile
```

### 2. Run the Application
```bash
mvn spring-boot:run
```

### 3. Alternative: Run JAR
```bash
mvn clean package
java -jar target/csv-processor-0.0.1-SNAPSHOT.jar
```

## Usage

### Automatic Job Execution
The batch job runs automatically on application startup, processing the sample CSV data.

### Manual Job Execution
Trigger the job manually via REST API:
```bash
curl http://localhost:8080/run-job
```

### Monitoring
- View application logs for job execution details
- Check database for processed records:
```sql
SELECT * FROM person;
```

## Sample Data

The application includes sample CSV data ([person-data.csv](src/main/resources/person-data.csv)):
```csv
Jill,Doe
Joe,Doe
Justin,Doe
Jane,Doe
John,Doe
```

After processing, names are converted to uppercase and stored in the database.

## Key Components

### Person Record
[Person.java](src/main/java/com/itools/record/Person.java)
```java
public record Person(String firstName, String lastName) {}
```

### Item Processor
[PersonItemProcessor.java](src/main/java/com/itools/component/PersonItemProcessor.java) - Transforms person data by converting names to uppercase:
```java
@Override
public Person process(Person person) throws Exception {
    String firstName = person.firstName().toUpperCase();
    String lastName = person.lastName().toUpperCase();
    return new Person(firstName, lastName);
}
```

### Job Configuration
[AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java)
- **Reader**: `FlatFileItemReader` for CSV parsing
- **Processor**: `PersonItemProcessor` for data transformation
- **Writer**: `JdbcBatchItemWriter` for database persistence
- **Step**: Chunk-based processing with transaction management
- **Job**: Complete batch job with listener

## Logging

The application provides comprehensive logging:
- Job start/completion notifications
- Processed record verification
- Scheduled task execution
- Error handling and reporting

## Development

### Adding New Processors
1. Implement `ItemProcessor<InputType, OutputType>`
2. Register as Spring component
3. Configure in [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java)

### Customizing Chunk Size
Modify the chunk size in [AppConfiguration.csvImportStep()](src/main/java/com/itools/config/AppConfiguration.java#L55):
```java
.<Person, Person>chunk(10, dataSourceTransactionManager) // Changed from 3 to 10
```

### Adding New Job Steps
1. Create new step bean in [AppConfiguration.java](src/main/java/com/itools/config/AppConfiguration.java)
2. Chain steps in job configuration:
```java
.start(step1)
.next(step2)
.build()
```

## Troubleshooting

### Common Issues
1. **Database Connection**: Verify MySQL is running and credentials are correct
2. **Schema Issues**: Ensure database schema is created (check [schema-all.sql](src/main/resources/schema-all.sql))
3. **File Not Found**: Verify [person-data.csv](src/main/resources/person-data.csv) exists in resources folder
4. **Port Conflicts**: Default port 8080 - change in [application.properties](src/main/resources/application.properties) if needed

### Logs Location
Check application logs for detailed error information and job execution status.

## Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## License

This project is for educational purposes and demonstrates Spring Batch capabilities.