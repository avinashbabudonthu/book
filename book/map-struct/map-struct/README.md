# Java Date Formatting and Object Mapping Project

## Source Files

### Date Formatting Package

[DateFormatTest.java](src/main/java/com/java/date/format/DateFormatTest.java)
This is the main entry point for the date formatting example. It demonstrates the usage of date formatting and mapping between StudentEntity and StudentModel objects.

[StudentEntity.java](src/main/java/com/java/date/format/StudentEntity.java)
Represents a student entity with name, course, and joining dates (Date and GregorianCalendar).

[StudentMapper.java](src/main/java/com/java/date/format/StudentMapper.java)
A MapStruct mapper interface that provides mapping functionality between StudentEntity and StudentModel objects, with custom date conversion methods.

[StudentModel.java](src/main/java/com/java/date/format/StudentModel.java)
Represents a student's information with name, course, and joining dates as strings.

### Example001 Package

[Example001Test.java](src/main/java/com/java/example001/Example001Test.java)
A test class that verifies the mapping between StudentEntity and StudentModel classes.

[StudentEntity.java](src/main/java/com/java/example001/StudentEntity.java)
Represents a simple student entity with name and course.

[StudentMapper.java](src/main/java/com/java/example001/StudentMapper.java)
A MapStruct mapper interface for converting between StudentEntity and StudentModel objects.

[StudentModel.java](src/main/java/com/java/example001/StudentModel.java)
Represents a student with name and course, used as a data model or transfer object.

### Other Files

[pom.xml](pom.xml)
The Maven project configuration file for the project, specifying dependencies and build settings.

This project demonstrates date formatting and object mapping in Java using MapStruct and custom date conversion methods.

The project includes two main components: a date formatting example and a basic student information management system. It showcases the use of MapStruct for object mapping between entity and model classes, with a focus on handling different date representations.

## Repository Structure

```
.
├── pom.xml
├── README.md
└── src
    └── main
        ├── java
        │   └── com
        │       └── java
        │           ├── date
        │           │   └── format
        │           │       ├── DateFormatTest.java
        │           │       ├── StudentEntity.java
        │           │       ├── StudentMapper.java
        │           │       └── StudentModel.java
        │           ├── example001
        │           │   ├── Example001Test.java
        │           │   ├── StudentEntity.java
        │           │   ├── StudentMapper.java
        │           │   └── StudentModel.java
        │           └── HelloWorld.java
        └── resources
            └── log4j.xml
```

Key Files:
- `pom.xml`: Maven project configuration file
- `src/main/java/com/java/date/format/DateFormatTest.java`: Main entry point for date formatting example
- `src/main/java/com/java/example001/Example001Test.java`: Test class for basic student information management
- `src/main/java/com/java/date/format/StudentMapper.java`: MapStruct mapper for date formatting example
- `src/main/java/com/java/example001/StudentMapper.java`: MapStruct mapper for basic student information

## Usage Instructions

### Installation

Prerequisites:
- Java Development Kit (JDK) 21
- Apache Maven 3.6.3 or higher

To install the project, follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the project root directory.
3. Run the following command to build the project:

```bash
mvn clean install
```

### Getting Started

To run the date formatting example:

```bash
java -cp target/map-struct-1.0-SNAPSHOT.jar com.java.date.format.DateFormatTest
```

To run the basic student information test:

```bash
java -cp target/map-struct-1.0-SNAPSHOT.jar com.java.example001.Example001Test
```

### Configuration

The project uses MapStruct for object mapping. The mapping configurations can be found in the `StudentMapper` interfaces in both the `date.format` and `example001` packages.

### Common Use Cases

1. Date Formatting Example:

```java
StudentEntity studentEntity = new StudentEntity();
studentEntity.setName("John Doe");
studentEntity.setCourse("Computer Science");
studentEntity.setJoiningDate1(new Date());
studentEntity.setJoiningDate2(new GregorianCalendar());

StudentModel studentModel = StudentMapper.INSTANCE.entityToModel(studentEntity);
System.out.println(studentModel);

StudentEntity convertedEntity = StudentMapper.INSTANCE.modelToEntity(studentModel);
System.out.println(convertedEntity);
```

2. Basic Student Information Management:

```java
StudentEntity studentEntity = new StudentEntity();
studentEntity.setName("Jane Smith");
studentEntity.setCourse("Mathematics");

StudentModel studentModel = StudentMapper.INSTANCE.entityToModel(studentEntity);
System.out.println(studentModel);
```

### Testing & Quality

The project includes test classes (`DateFormatTest` and `Example001Test`) that demonstrate the functionality of the mappers and date formatting. To run the tests, use the following command:

```bash
mvn test
```

### Troubleshooting

Common issues and solutions:

1. MapStruct compilation errors:
   - Problem: MapStruct mapper is not generated.
   - Solution: Ensure that the MapStruct annotation processor is correctly configured in the `pom.xml` file and that you've run `mvn clean install`.

2. Date parsing errors:
   - Problem: `ParseException` when converting date strings.
   - Solution: Verify that the date strings match the expected format "dd-MM-yyyy".

3. Logging configuration issues:
   - Problem: Logging statements are not visible.
   - Solution: Check that the `log4j.xml` file is properly configured in the `src/main/resources` directory.

## Data Flow

The data flow in this project primarily revolves around the conversion between entity and model objects, with a focus on date formatting.

1. Entity objects (`StudentEntity`) are created with raw data, including `Date` and `GregorianCalendar` objects for date fields.
2. The `StudentMapper` interface, implemented by MapStruct, converts entity objects to model objects (`StudentModel`).
3. During this conversion, date fields are formatted as strings using the "dd-MM-yyyy" pattern.
4. The resulting `StudentModel` objects contain string representations of dates.
5. The process can be reversed, converting `StudentModel` objects back to `StudentEntity` objects, parsing the date strings back into `Date` and `GregorianCalendar` objects.

```
[StudentEntity] -> [StudentMapper] -> [StudentModel]
     ^                                     |
     |                                     |
     +--------- [StudentMapper] <----------+
```

Note: The `StudentMapper` interface provides custom methods `gregorianCalendarToString` and `stringToGregorianCalendar` to handle the conversion between `GregorianCalendar` and `String` representations of dates.