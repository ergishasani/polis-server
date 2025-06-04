# Polis Server Skeleton

A simple Spring Boot-based RESTful web service for managing Courses, Teachers, and Students.
This project uses Spring Data JPA for persistence, MapStruct for DTO-to-entity mapping, and Lombok to reduce boilerplate. It exposes endpoints to create, read, update, delete, and associate these entities.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Prerequisites](#prerequisites)
4. [Getting Started](#getting-started)

    * [Clone the Repository](#clone-the-repository)
    * [Configure the Database](#configure-the-database)
    * [Build the Project](#build-the-project)
    * [Run the Application](#run-the-application)
5. [Configuration](#configuration)

    * [`application.properties`](#applicationproperties)
6. [Database Schema](#database-schema)
7. [Project Structure](#project-structure)
8. [REST API Endpoints](#rest-api-endpoints)

    * [Course Endpoints](#course-endpoints)
    * [Teacher Endpoints](#teacher-endpoints)
    * [Student Endpoints](#student-endpoints)
    * [Request/Response DTOs](#requestresponse-dtos)
    * [Error Handling](#error-handling)
9. [Mapping with MapStruct](#mapping-with-mapstruct)
10. [Running Tests](#running-tests)
11. [Troubleshooting & Tips](#troubleshooting--tips)
12. [License](#license)

---

## Project Overview

**Polis Server Skeleton** is a boilerplate Spring Boot project demonstrating a standard layered architecture with:

* **Controllers** for handling HTTP requests
* **Services** for business logic
* **Repositories** for data access (Spring Data JPA)
* **Entities** representing `Course`, `Teacher`, and `Student`
* **DTOs** (Data Transfer Objects) for request/response payloads
* **MapStruct Mappers** to convert between Entities and DTOs
* **Lombok** to generate getters/setters, constructors, `toString`, etc.
* **HikariCP** as the connection pool (via Spring Boot’s auto-configuration)
* **MySQL** (or any JPA-compatible RDBMS) as the backing store

The application provides endpoints to:

* Create/update (upsert) `Course`, `Teacher`, and `Student` records
* Filter (search) with pagination by simple string
* Delete entities (with “cannot delete if relationships exist” safeguards)
* Associate and disassociate `Teacher` ↔ `Course`
* Associate and disassociate `Student` ↔ `Course`

---

## Technology Stack

* **Spring Boot 3.4.4**
* **Spring Data JPA** (Hibernate 6.6)
* **MapStruct 1.5.5.Final**
* **Lombok 1.18.36**
* **H2 / MySQL** (via JDBC/MySQL Connector)
* **Java 17+**
* **Maven 3.9+**
* **Tomcat 10.1 (embedded)**

---

## Prerequisites

1. **Java Development Kit (JDK) 17 or higher**
   Ensure `JAVA_HOME` is pointing to a JDK 17+ installation.

   ```bash
   java -version
   # should show something like "openjdk version "17.x.x" or "Oracle JDK 17.x.x"
   ```

2. **Maven 3.9+**
   Make sure you have Apache Maven installed (or you can use the included Maven Wrapper).

   ```bash
   mvn -version
   # or if using Maven Wrapper: ./mvnw -version
   ```

3. **Database**

    * MySQL 8.x (or any other JPA-compatible relational database)
    * Alternatively, you can use H2 (in-memory) for quick local testing, though this README assumes MySQL.

4. **IDE** (recommended)

    * IntelliJ IDEA, Eclipse, VS Code, or any Java IDE supporting Spring Boot.

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/<your-username>/polis-server-skeleton.git
cd polis-server-skeleton
```

> **Note:** If you plan to use the Maven Wrapper (`mvnw`/`mvnw.cmd`), ensure it has executable permissions on Linux/macOS:
>
> ```bash
> chmod +x mvnw
> ```

### Configure the Database

1. **Create a MySQL database** (e.g., `polis_db`):

   ```sql
   CREATE DATABASE polis_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **Create a user** and grant privileges (example):

   ```sql
   CREATE USER 'polis_user'@'localhost' IDENTIFIED BY 'ChangeMe123!';
   GRANT ALL PRIVILEGES ON polis_db.* TO 'polis_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Edit `src/main/resources/application.properties`** (see [Configuration](#configuration) below) to match your DB credentials, e.g.:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/polis_db
   spring.datasource.username=polis_user
   spring.datasource.password=ChangeMe123!

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

   # Optional: show SQL in the console
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

### Build the Project

From the project root, run:

```bash
./mvnw clean package
# or if you have Maven installed globally:
# mvn clean package
```

* This will compile all sources, run annotation processors (Lombok & MapStruct), and produce a JAR in `target/appserver-0.0.1-SNAPSHOT.jar`.
* You should see `BUILD SUCCESS` if everything compiles correctly.

### Run the Application

#### Using Maven

```bash
./mvnw spring-boot:run
```

#### Using the Packaged JAR

```bash
java -jar target/appserver-0.0.1-SNAPSHOT.jar
```

By default, the application will start on **port 8080**. You can verify it by opening your browser or sending a request to:

```
http://localhost:8080/actuator/health
```

---

## Configuration

All configuration is driven via `src/main/resources/application.properties`. Below is a sample template:

```properties
# ===============================
# Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
# ===============================
spring.datasource.url=jdbc:mysql://localhost:3306/polis_db
spring.datasource.username=polis_user
spring.datasource.password=ChangeMe123!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===============================
# Spring JPA / Hibernate
# ===============================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# ===============================
# Server port (optional)
# ===============================
# server.port=8080

# ===============================
# Logging
# ===============================
logging.level.org.springframework=INFO
logging.level.al.polis.appserver=DEBUG
```

> **Note**:
>
> * `spring.jpa.hibernate.ddl-auto=update` will automatically create/update tables on startup. For production, consider using `validate` or `none` with proper migrations (Flyway or Liquibase).

---

## Database Schema

The project defines three JPA entities:

1. **Course** (`al.polis.appserver.model.Course`)

    * `id` (PK, auto-generated)
    * `code` (String)
    * `title` (String)
    * `description` (String)
    * `year` (Integer)
    * `teacher_id` (FK → `Teacher.id`)
    * `students` (OneToMany → `Student.id`)

2. **Teacher** (`al.polis.appserver.model.Teacher`)

    * `id` (PK, auto-generated)
    * `firstName` (String)
    * `lastName` (String)
    * `title` (String)
    * `courses` (OneToMany → `Course.id`)

3. **Student** (`al.polis.appserver.model.Student`)

    * `id` (PK, auto-generated)
    * `firstName` (String)
    * `lastName` (String)
    * `serialNumber` (String)
    * `course_id` (FK → `Course.id`)

*Hibernate will automatically generate these tables (with `DDL-AUTO=update`), including foreign key constraints.*

---

## Project Structure

```
polis-server-skeleton/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── al.polis.appserver/
│   │   │       ├── AppserverApplication.java
│   │   │       ├── communication/           # Error handling, status DTOs, filters, sorting, etc.
│   │   │       ├── controller/              # REST controllers
│   │   │       │   ├── CourseController.java
│   │   │       │   ├── TeacherController.java
│   │   │       │   └── StudentController.java
│   │   │       ├── dto/                     # Request and Response DTOs
│   │   │       │   ├── CourseDto.java
│   │   │       │   ├── TeacherDto.java
│   │   │       │   ├── StudentDto.java
│   │   │       │   ├── LongIdDto.java
│   │   │       │   ├── SimpleStringFilterDto.java
│   │   │       │   ├── PaginationDto.java
│   │   │       │   ├── CourseStudentAssocDto.java
│   │   │       │   └── CourseTeacherAssocDto.java
│   │   │       ├── exception/                # Custom runtime exception
│   │   │       │   └── TestServerRuntimeException.java
│   │   │       ├── mapper/                   # MapStruct mapper interfaces
│   │   │       │   ├── CourseMapper.java
│   │   │       │   ├── TeacherMapper.java
│   │   │       │   └── StudentMapper.java
│   │   │       ├── model/                    # JPA Entities
│   │   │       │   ├── Course.java
│   │   │       │   ├── Teacher.java
│   │   │       │   └── Student.java
│   │   │       ├── repo/                     # Spring Data JPA repositories
│   │   │       │   ├── CourseRepository.java
│   │   │       │   ├── TeacherRepository.java
│   │   │       │   └── StudentRepository.java
│   │   │       ├── service/                  # Service interfaces
│   │   │       │   ├── CourseService.java
│   │   │       │   ├── TeacherService.java
│   │   │       │   └── StudentService.java
│   │   │       └── service/impl/             # Service implementations
│   │   │           ├── CourseServiceImpl.java
│   │   │           ├── TeacherServiceImpl.java
│   │   │           └── StudentServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── (other resources…)
│   └── test/
│       └── java/                             # (Optional) Unit tests
│
├── mvnw
├── mvnw.cmd
├── pom.xml                                  # Maven configuration
└── README.md                                # (You are here)
```

---

## REST API Endpoints

Below is a consolidated list of all available endpoints with URI, HTTP method, request JSON examples, and response structure. All endpoints return a JSON wrapper object:

```jsonc
{
  "status":[ /* List<ServerStatus> */ ],
  "data": /* T or null */
}
```

* `status` is a list of zero or more `ServerStatus` messages (errors, warnings, info).
* `data` is a generic payload: either `null`, a single DTO, or a paged slice of DTOs.

### Common DTO Wrappers

* **`RespSingleDto<T>`**
  A wrapper for a single object (`T data`) and `List<ServerStatus> status`.

* **`RespSliceDto<T>`**
  A wrapper for a `Slice<T> slice` and `List<ServerStatus> status`. Supports pagination via Spring Data’s `Slice`.

---

### Course Endpoints

#### 1. Upsert Course (Create or Update)

* **URL:**

  ```
  POST /upsertCourse
  ```
* **Request Body:**

  ```json
  {
    "id": 1,                 
    "code": "CS101",
    "title": "Introduction to CS",
    "description": "Basics of computer science",
    "year": 2025,
    "teacher": {             
      "id": 2
    },
    "students": [            
      { "id": 5 },
      { "id": 7 }
    ]
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": {
      "id": 1,
      "code": "CS101",
      "title": "Introduction to CS",
      "description": "Basics of computer science",
      "year": 2025,
      "teacher": {
        "id": 2,
        "firstName": "Alice",
        "lastName": "Smith",
        "title": "Dr."
      },
      "students": [
        {
          "id": 5,
          "firstName": "Bob",
          "lastName": "Brown",
          "serialNumber": "S123",
          "course": null
        }
      ]
    }
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING` (if the payload is `null`)

#### 2. Filter Courses (Search + Pagination)

* **URL:**

  ```
  POST /filterCourses
  ```
* **Request Body:**

  ```json
  {
    "filter": "CS",                  
    "pagination": {
      "pageNumber": 0,
      "pageSize": 10
    }
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "slice": {
      "content": [
        {
          "id": 1,
          "code": "CS101",
          "title": "Introduction to CS",
          "description": "Basics of computer science",
          "year": 2025,
          "teacher": null,
          "students": []
        },
        …
      ],
      "pageable": { … },        // Spring Data’s Pageable metadata
      "last": false,            // If this is the last page
      "totalPages": 5,          // (optional; Spring Boot may not show totalPages for Slice)
      "totalElements": 50,      // (optional for Slice)
      "size": 10,
      "number": 0,
      "sort": { … },            // Sort metadata
      "first": true,
      "numberOfElements": 10,
      "empty": false,
      "hasNext": true,
      "hasPrevious": false
    }
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING` (if `filter` or `pagination` is missing)

#### 3. Delete Course

* **URL:**

  ```
  POST /deleteCourse
  ```
* **Request Body:**

  ```json
  {
    "id": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `COURSE_NOT_FOUND` (if `id` is missing or no such course exists)
    * `DELETE_COURSE_NOT_ALLOWED` (if the course has a teacher or any students)

#### 4. Get Course by ID

* **URL:**

  ```
  POST /getCourse
  ```
* **Request Body:**

  ```json
  {
    "id": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": {
      "id": 1,
      "code": "CS101",
      "title": "Introduction to CS",
      "description": "Basics of computer science",
      "year": 2025,
      "teacher": { … },
      "students": [ … ]
    }
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING` (if `id` is `null` or payload missing)
    * `COURSE_NOT_FOUND` (if no course with that ID exists)

#### 5. Associate Teacher ↔ Course

* **URL:**

  ```
  POST /associateTeacherToCourse
  ```
* **Request Body:**

  ```json
  {
    "idTeacher": 2,
    "idCourse": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING`, `COURSE_NOT_FOUND`
    * `TEACHER_MISSING`, `TEACHER_NOT_FOUND`

#### 6. Remove Teacher from Course

* **URL:**

  ```
  POST /removeTeacherFromCourse
  ```
* **Request Body:**

  ```json
  {
    "idTeacher": 2,
    "idCourse": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `COURSE_NOT_FOUND`
    * `TEACHER_MISSING`, `TEACHER_NOT_FOUND`

---

### Teacher Endpoints

#### 1. Upsert Teacher

* **URL:**

  ```
  POST /upsertTeacher
  ```
* **Request Body:**

  ```json
  {
    "id": 2,            
    "firstName": "Alice",
    "lastName": "Smith",
    "title": "Dr.",
    "courses": [        
      { "id": 1 }
    ]
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": {
      "id": 2,
      "firstName": "Alice",
      "lastName": "Smith",
      "title": "Dr.",
      "courses": [
        {
          "id": 1,
          "code": "CS101",
          "title": "Introduction to CS",
          "description": "...",
          "year": 2025,
          "teacher": null,
          "students": []
        }
      ]
    }
  }
  ```
* **Possible Status Messages:**

    * `TEACHER_MISSING` (if payload is missing)

#### 2. Filter Teachers

* **URL:**

  ```
  POST /filterTeachers
  ```
* **Request Body:**

  ```json
  {
    "filter": "Ali",                   
    "pagination": {
      "pageNumber": 0,
      "pageSize": 10
    }
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "slice": {
      "content": [
        {
          "id": 2,
          "firstName": "Alice",
          "lastName": "Smith",
          "title": "Dr.",
          "courses": [ … ]
        },
        …
      ],
      "pageable": { … },
      "last": false,
      ... (other slice fields)
    }
  }
  ```
* **Possible Status Messages:**

    * `TEACHER_MISSING` (if `filter` or `pagination` is missing)

#### 3. Delete Teacher

* **URL:**

  ```
  POST /deleteTeacher
  ```
* **Request Body:**

  ```json
  {
    "id": 2
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `TEACHER_MISSING`, `TEACHER_NOT_FOUND`
    * `DELETE_TEACHER_NOT_ALLOWED` (if the teacher has assigned courses)

#### 4. Get Teacher by ID

* **URL:**

  ```
  POST /getTeacher
  ```
* **Request Body:**

  ```json
  {
    "id": 2
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": {
      "id": 2,
      "firstName": "Alice",
      "lastName": "Smith",
      "title": "Dr.",
      "courses": [ … ]
    }
  }
  ```
* **Possible Status Messages:**

    * `TEACHER_MISSING`
    * `TEACHER_NOT_FOUND`

---

### Student Endpoints

#### 1. Upsert Student

* **URL:**

  ```
  POST /upsertStudent
  ```
* **Request Body:**

  ```json
  {
    "id": 5,                
    "firstName": "Bob",
    "lastName": "Brown",
    "serialNumber": "S123",
    "course": { "id": 1 }    
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": {
      "id": 5,
      "firstName": "Bob",
      "lastName": "Brown",
      "serialNumber": "S123",
      "course": {
        "id": 1,
        "code": "CS101",
        "title": "Introduction to CS",
        ...
      }
    }
  }
  ```
* **Possible Status Messages:**

    * `STUDENT_MISSING`

#### 2. Filter Students

* **URL:**

  ```
  POST /filterStudents
  ```
* **Request Body:**

  ```json
  {
    "filter": "Bob",                
    "pagination": {
      "pageNumber": 0,
      "pageSize": 10
    }
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "slice": {
      "content": [
        {
          "id": 5,
          "firstName": "Bob",
          "lastName": "Brown",
          "serialNumber": "S123",
          "course": { … }
        },
        …
      ],
      "pageable": { … },
      "last": false,
      … (other pagination fields)
    }
  }
  ```
* **Possible Status Messages:**

    * `STUDENT_MISSING`

#### 3. Delete Student

* **URL:**

  ```
  POST /deleteStudent
  ```
* **Request Body:**

  ```json
  {
    "id": 5
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `STUDENT_MISSING`, `STUDENT_NOT_FOUND`
    * `DELETE_STUDENT_NOT_ALLOWED` (if student is associated to a course)

> **Note:** In the code, the constant used is `DELETE_COURSE_NOT_ALLOWED` for the student deletion check—be mindful that error severities might need custom tweaking if you want a distinct message for “student has a course.”

#### 4. Associate Student ↔ Course

* **URL:**

  ```
  POST /associateStudentToCourse
  ```
* **Request Body:**

  ```json
  {
    "idStudent": 5,
    "idCourse": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING`, `COURSE_NOT_FOUND`
    * `STUDENT_MISSING`, `STUDENT_NOT_FOUND`

#### 5. Remove Student from Course

* **URL:**

  ```
  POST /removeStudentFromCourse
  ```
* **Request Body:**

  ```json
  {
    "idStudent": 5,
    "idCourse": 1
  }
  ```
* **Response (`200 OK`):**

  ```jsonc
  {
    "status": [],
    "data": null
  }
  ```
* **Possible Status Messages:**

    * `COURSE_MISSING`, `COURSE_NOT_FOUND`
    * `STUDENT_MISSING`, `STUDENT_NOT_FOUND`

---

### Request/Response DTOs

Below is a summary of the core DTO classes used for requests and responses.

#### `LongIdDto`

Carries a single `Long id` field for delete/get requests.

```java
@Data
public class LongIdDto {
    private Long id;
}
```

#### `PaginationDto`

Carries `pageNumber` and `pageSize` for slice queries.

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private Integer pageNumber;
    private Integer pageSize;
}
```

#### `SimpleStringFilterDto`

Carries a `String filter` and a `PaginationDto pagination`.

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleStringFilterDto {
    private String filter;
    private PaginationDto pagination;
}
```

#### `CourseDto`

Used for create/update and fetching course details.

```java
@Data
public class CourseDto {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Integer year;
    private TeacherDto teacher;
    private List<StudentDto> students;
}
```

#### `TeacherDto`

Used for create/update and fetching teacher details.

```java
@Data
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String title;
    private List<CourseDto> courses;
}
```

#### `StudentDto`

Used for create/update and fetching student details.

```java
@Data
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String serialNumber;
    private CourseDto course;
}
```

#### `CourseTeacherAssocDto` & `CourseStudentAssocDto`

Carry `idTeacher` + `idCourse` or `idStudent` + `idCourse` for association endpoints.

```java
@Data
public class CourseTeacherAssocDto {
    private Long idTeacher;
    private Long idCourse;
}

@Data
public class CourseStudentAssocDto {
    private Long idStudent;
    private Long idCourse;
}
```

---

### Error Handling

* All exceptions are wrapped in a custom unchecked exception: `TestServerRuntimeException`.
* Whenever a required field or entity is missing/not found, the service-layer method:

    1. Calls `ErrorContext.addStatusMessage(ServerErrorEnum.*)` to record a new `ServerStatus`.
    2. Throws `TestServerRuntimeException("...")`.
* The controllers catch any `Exception` and log it with SLF4J:

  ```java
  @PostMapping("/upsertCourse")
  @ResponseBody
  public RespSingleDto<CourseDto> upsertCourse(@RequestBody CourseDto course) {
      CourseDto res = null;
      try {
          res = courseService.upsertCourse(course);
      } catch (Exception ex) {
          log.error(ex.getMessage(), ex);
      }
      return new RespSingleDto<>(res, ErrorContext.readAndClean());
  }
  ```
* At the end of each request, the controller returns a `RespSingleDto<T>` or `RespSliceDto<T>` containing:

    * `data` (the DTO or slice)
    * `status` (a `List<ServerStatus>` pulled from `ErrorContext.readAndClean()`)

If there were no errors, `status` will be an empty list.

---

## Mapping with MapStruct

The project uses [MapStruct](https://mapstruct.org/) to generate mappers that convert between entity classes (e.g., `Course`, `Teacher`, `Student`) and their DTO counterparts (`CourseDto`, `TeacherDto`, `StudentDto`). Key points:

1. **Mapper Interfaces** exist under `al.polis.appserver.mapper`, each annotated with:

   ```java
   @Mapper(componentModel = "spring")
   public interface CourseMapper {
       CourseDto toDto(Course source);
       Course toEntity(CourseDto dto);
   }
   ```

2. **Avoiding Circular Dependencies**

    * In `CourseMapper`, if you call `@Mapping(target="students", ignore = true)`, you avoid mapping back into `StudentMapper` and vice versa.
    * `StudentMapper` is defined without using `CourseMapper` directly so that mappers do not depend on each other in a cycle.

3. **Annotation Processors**

    * The Maven Compiler Plugin is configured to register MapStruct (and Lombok) as annotation processors.
    * Generated implementations (e.g., `CourseMapperImpl`, `StudentMapperImpl`, `TeacherMapperImpl`) get auto-registered as Spring beans because of `componentModel = "spring"`.

If you run into a circular reference at startup, you can break it by adjusting `uses = { CourseMapper.class }` or using `@Context` or manually wiring one mapper in another—but the provided code avoids circular references by ignoring fields pointing back to the other entity’s mapper.

---

## Running Tests

*No unit tests are included by default.* If you add JUnit tests under `src/test/java`, run:

```bash
./mvnw test
# or
mvn test
```

When adding tests, make sure to annotate with `@SpringBootTest` or use mocks (e.g., Mockito) for service-layer testing.

---

## Troubleshooting & Tips

1. **Lombok Annotations Not Recognized**

    * Install the Lombok plugin in your IDE and enable annotation processing.
    * In IntelliJ:

        * **File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing**
    * In Eclipse:

        * Install Lombok JAR via `java -jar lombok.jar` and follow instructions.

2. **MapStruct Processor Errors**

    * Ensure the MapStruct annotation processor is on your classpath as configured in `pom.xml`:

      ```xml
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.10.1</version>
        <configuration>
          <source>${java.version}</source>
          <target>${java.version}</target>
          <annotationProcessorPaths>
            <path>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <version>${lombok.version}</version>
            </path>
            <path>
              <groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId>
              <version>${mapstruct.version}</version>
            </path>
            <path>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok-mapstruct-binding</artifactId>
              <version>${mapstruct-lombok.version}</version>
            </path>
          </annotationProcessorPaths>
        </configuration>
      </plugin>
      ```
    * Rebuild (e.g., `./mvnw clean compile`) to regenerate mapper implementations.

3. **Circular Bean Dependencies**

    * If Spring complains about a circular dependency between mappers (e.g., `CourseMapperImpl ↔ StudentMapperImpl`), adjust the `@Mapper` definitions to ignore fields that would cause the cycle, or set `spring.main.allow-circular-references=true` in `application.properties` (not recommended in production).

4. **Database Dialect Warnings**

    * Hibernate might warn about unsupported MySQL versions. Consider explicitly specifying a compatible dialect (e.g., `org.hibernate.dialect.MySQL8Dialect`) if you run MySQL 8+.

5. **405 Method Not Allowed (Whitelabel Error Page)**

    * Happens if you hit an endpoint (e.g., GET `/upsertCourse`) with the wrong HTTP method. Make sure to use `POST` for all provided endpoints.

---

## License

This project is provided as-is under the [MIT License](LICENSE). Feel free to clone, modify, and use it as a starting point for your own Spring Boot microservices or RESTful APIs.

```text
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy
of this project and associated documentation files (the "Software"), to deal
in the Software without restriction...

[Full text of the MIT License goes here.]
```

