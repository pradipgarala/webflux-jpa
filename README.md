# Java Webflux CRUD Application: Department and Employee Management

## HomePage : http://localhost:8080/

## postman-collection
        /resources/web-flux.postman_collection.json

### Sample Data

#### Department
| id | name        |
|----|-------------|
| 1  | account     |
| 2  | engineering |
| 3  | hr          |


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
