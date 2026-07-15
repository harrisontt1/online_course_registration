# Testing Procedures
## Introdction

The testing procedures describe how the Online Course Registration System will be validated using both JUnit and TestNG. JUnit is used for focused unit testing of Java classes and validation logic, while TestNG is used for broader integration testing that involves JDBC, MySQL, enrollment workflows, and database setup or teardown. These procedures support consistent execution through Gradle and help ensure that test results are repeatable, traceable, and aligned with the system requirements.

For JUnit 5 tests, use the standard Gradle test task:

```
./gradlew test --tests "online_registration.junit.*"
```

Example: 
```
./gradlew test --tests "online_registration.junit.TestEnrollment"
````

For TestNG tests, use the dedicated TestNG Gradle task:

```
./gradlew :testNG --tests "online_registration.testng.*"
```

Example: 

```
./gradlew :testNG --tests "online_registration.testng.EnrollmentFlowTest"
```
These commands separate unit testing from integration testing. The standard Gradle test task is configured for JUnit 5 tests in the online_registration.junit package, while the custom testNG task is configured for TestNG tests in the online_registration.testng package. This separation is necessary because JUnit and TestNG use different test engines and execution rules. Routing a TestNG class through the standard JUnit test task can cause Gradle to report that no matching tests were found, even when the TestNG class exists. Using separate tasks ensures that each test suite is discovered and executed by the correct framework.

## JUnit Testing Procedure

JUnit testing is used to verify individual Java classes, methods, and business rules in isolation before the application is tested as a complete system. These tests focus on the domain model and validation logic, including classes such as Student, Course, Section, Enrollment, RegistrationSystem, and ValidationEngine. The goal is to confirm that each component produces the expected result without depending on the full web interface or a live database connection.

Create or update JUnit test classes in the JUnit test package using the project naming convention, such as ```TestStudent```, ```TestCourse```, ```TestSection```, TestEnrollment, or TestValidationEngine.

Write test methods with clear names that describe the expected behavior, such as ```testStudentCanEnrollWhenSectionHasCapacity```, ```testSectionCapacityCannotBeExceeded```, or ```testInvalidCourseDataFailsValidation```.

Prepare simple test data directly inside the test method or setup method so each test can run independently and produce the same result every time.

Use JUnit assertions to compare expected and actual outcomes, including boolean results, object values, enrollment counts, validation messages, and exception handling.

Run the JUnit test suite through the standard Gradle test task to verify that all unit tests compile and pass successfully.

For JUnit 5 tests, use the standard Gradle test task:

./gradlew test --tests "online_registration.junit.*"

Example: ./gradlew test --tests "online_registration.junit.TestEnrollment"

Review failed tests, identify whether the failure is caused by test data, application logic, or an incorrect expected result, and correct the issue before moving to integration testing.

Repeat the JUnit tests after code changes to confirm that updates did not break existing functionality.

JUnit tests should be run frequently during development because they provide quick feedback on the correctness of core logic. These tests are especially useful for validating prerequisite checks, capacity rules, schedule conflict logic, enrollment status updates, and basic object behavior before those features are connected to servlets, JSP pages, JDBC, or MySQL.

## TestNG Testing Procedure

TestNG testing is used for comprehensive integration testing where multiple parts of the application must work together. These tests validate database connectivity, JDBC operations, MySQL table relationships, enrollment transactions, authentication workflows, and end-to-end behavior for the Course Catalog and Enrollment Module. TestNG is appropriate for these tests because it supports setup and teardown steps, ordered execution, grouped test cases, and broader workflow validation.

Confirm that MySQL is running and that the registrationdb database is available before executing TestNG tests.

Verify that the required tables exist, including student, course, section, enrollment, and any additional tables used for users, prerequisites, completed courses, terms, or instructors.

Create TestNG classes in the TestNG test package using descriptive names such as ```DatabaseConnectivityTest```, ```EnrollmentFlowTest```, ```CourseCatalogTest```, ```SectionCapacityIntegrationTest```, or ```AuthenticationIntegrationTest```.

Use setup methods to establish database connections, insert controlled test records, and prepare predictable test conditions.

Execute integration tests that validate course searching, course detail retrieval, enrollment creation, course withdrawal, schedule retrieval, section capacity checks, prerequisite checks, and database update behavior.

Use teardown methods to remove temporary test records or reset test data so later test runs are not affected by previous results.

Run the TestNG suite through the dedicated Gradle TestNG task instead of the standard JUnit test task.

For TestNG tests, use the dedicated TestNG Gradle task:

```
./gradlew :testNG --tests "online_registration.testng.*"
```

Example: 
```
./gradlew :testNG --tests "online_registration.testng.EnrollmentFlowTest"
```

Review TestNG output to confirm that database connectivity, SQL operations, enrollment workflows, and validation paths completed successfully.

Investigate failed tests by checking the Gradle output, database state, SQL constraints, JDBC connection settings, and expected test data.

Rerun the TestNG suite after fixes to confirm that the issue has been resolved and that no regression errors were introduced.

The TestNG procedures should be performed after the JUnit tests pass because integration tests depend on multiple components working correctly together. These procedures are especially important for confirming that the application can connect to MySQL through JDBC, save and retrieve enrollment records, enforce referential integrity, and provide accurate feedback when a student attempts to enroll in, withdraw from, or view courses.

## Gradle Test Execution Procedure

Gradle is used to execute both testing frameworks while keeping their execution paths separate. The standard test task runs JUnit tests, while the dedicated TestNG task runs TestNG tests. This prevents framework conflicts and ensures that each test suite is executed by the correct test engine.

Run all JUnit tests with the standard Gradle test task: Use this command to run every JUnit 5 test class in the JUnit package:

```
./gradlew test --tests "online_registration.junit.*"
```

Run a specific JUnit test class by targeting the JUnit package and class name: Use this command when only one JUnit test class needs to be executed:

```
./gradlew test --tests "online_registration.junit.TestEnrollment"
```

Run all TestNG tests with the dedicated TestNG Gradle task: Use this command to run every TestNG test class in the TestNG package:

```
./gradlew :testNG --tests "online_registration.testng.*"
```

Run a specific TestNG class by targeting the TestNG package and class name through the TestNG task: Use this command when only one TestNG integration test class needs to be executed: 
```
./gradlew :testNG --tests "online_registration.testng.EnrollmentFlowTest"
```

If Gradle reports that no matching tests were found: Confirm that the test class is being executed through the correct task for its framework. JUnit tests should use the standard test task, while TestNG tests should use the dedicated testNG task.

After each test execution, the tester should record the test class name, test method or suite name, execution date, pass or fail status, and any defects discovered. Failed tests should be documented with the expected result, actual result, likely cause, and corrective action. Once corrections are made, the related JUnit or TestNG tests should be rerun to confirm that the defect has been resolved. 