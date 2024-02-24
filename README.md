# TODO
- @Valid @RequestBody - custom message
- insert employee
- test cases

### Sample Data

#### Department
| id | name        |
|----|-------------|
| 1  | account     |
| 2  | engineering |
| 2  | hr          |


#### Employee
| id | name    | age | email           |
|----|---------|-----|-----------------|
| 1  | alice   | 30  | alice@web.com   |
| 2  | bob     | 40  | bob@web.com     |
| 3  | charlie | 50  | charlie@web.com |

#### Employee_Department
| emp_id      | dep_id          |
|-------------|-----------------|
| 1 (alice)   | 1 (account)     |
| 2 (bob)     | 2 (engineering) |
| 3 (charlie) | 2 (account)     |
| 3 (charlie) | 2 (engineering) |


* The original package name 'com.example.web-flux' is invalid and this project uses 'com.example.webflux' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.8/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
