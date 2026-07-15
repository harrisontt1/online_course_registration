# Naming Conventions

## Purpose

This section defines the required naming standards for the Online Course Registration System. The goal is to keep source code, database objects, configuration files, and documentation consistent, readable, and maintainable across all team members.

## General Naming Rules

* Use clear, descriptive names that communicate the purpose of the item.

* Avoid abbreviations unless they are widely understood, such as ID, URL, JDBC, SQL, or DAO.

* Use consistent casing based on the artifact type.

* Use singular nouns for entity classes and database tables unless the project standard explicitly requires plural names.

* Do not use spaces, special characters, or reserved SQL/Java keywords in identifiers.

## Java Naming Conventions 
| Artifact       | Convention               | Example                     |
| -------------- | ------------------------ | --------------------------- |
| Class names    | PascalCase               | `Student` , `CourseSection` |
| Method         | camelCase; begin         | `registerStudent()` ,       |
| names          | with a verb              | `dropCourse()`              |
| Variable names | camelCase                | `studentId` , `courseTitle` |
| Constants      | UPPER_SNAKE_CASE         | `MAX_COURSE_CAPACITY`       |
| Packages       | lowercase, dot-separated | `com.registration.dao`      |

## Database Naming Conventions
| Database Object | Convention                                 | Example                                 |
| --------------- | ------------------------------------------ | --------------------------------------- |
| Database        | lowercase,                                 | ` regist                                |
| name            | no spaces                                  | rat ion db`                             |
| Table names     | lowercase snake_case ; singular nouns<br/> | `student` , `course ` , `section` <br/> |
| Column names    | lowercase snake_case                       | `student _id ` , `course _id `          |
| Primary keys    | `<table_na me >_id `                       | `enroll men t_id`                       |
| Foreign keys    | Match referenced primary key name          | `studen t _id ` , `sectio n _id `       |

## File and Directory Naming Conventions

* Java source files must match the public class name, such as Student.java.
* Configuration files should use lowercase names with hyphens or dots, such as application.properties.
* SQL script files should use lowercase snake_case, such as create_registration_tables.sql.
* Test files should append Test to the class being tested, such as StudentDaoTest.java. 


## JUnit and TestNG Naming Conventions

# Purpose

This section defines the naming convention and folder organization for JUnit and TestNG test classes in the Online Course Registration System. The goal is to keep tests grouped alphabetically, easy to locate, and clearly separated by testing purpose.

# Framework Usage Standard
| Framework | Primary Use | Recommended Test Type |
| ---- | --- | --- | 
| JUnit | Small, isolated checks against individual classes or validation rules. | Unit tests and validation tests.| 
| TestNG | Managed test suites, integration workflows, grouped scenarios, and database-backed testing. | Small, isolated checks against individual classes or validation rules. |

## Base Test Class Naming Pattern

All test classes should use the base naming pattern ```
Test<ClassName>```. 
This keeps test files alphabetically grouped near the production class they verify and makes sorting by name predictable in the project directory.
```
TestEnrollmentManager
TestDatabaseManager
TestSection
```

## Purpose-Based Suffix Labels

When a test class targets a specific test category, append suffix label after the class name. Use an underscore before the suffix so related tests remain alphabetically grouped while still showing their purpose.

| Suffix | Use Case | Example |
| --- | --- | --- | 
| _Validation | Tests input validation, rule checking, and expected invalid states. | TestEnrollmentManager_Validation  |
| _Integration | Tests interaction between classes, services, or external components. | TestEnrollmentManager_Integration |
| _Transaction  | Tests database commits, rollbacks, enrollment changes, and transactional workflows. | TestEnrollmentManager_Transaction  |

## Folder Structure

The project separates JUnit and TestNG tests into distinct directories under ```src/test/java/online_registration/```. This keeps unit-style validation tests separate from integration flows and database-backed test suites. 

```
src/test/java/online_registration/ 
    junit/ 
        TestEnrollmentManager.java 
        TestSectionValidation.java 
        TestStudentInputValidation.java 
    testng/ 
        TestEnrollmentManager_Integration.java 
        TestDatabaseManager_Transaction.java 
        TestSection_Validation.java 
```

## Naming Rules

* Use ```Test<ClassName>``` for the base test class name.
* Use suffix labels only when they add meaningful test-category information.
* Use ```_Validation```, ```_Integration```, and ```_Transaction``` consistently across the project.
* Place fast JUnit validation and unit tests in the junit/ directory.
* Place TestNG suite, integration, workflow, and database-backed tests in the testng/ directory. 
* Avoid vague names such as ```TestManager```, ```TestStuff```, or ```GeneralTest```. 