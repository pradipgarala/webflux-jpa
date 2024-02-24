CREATE TABLE IF NOT EXISTS department (
    dep_id IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    description varchar(255)
);

CREATE TABLE IF NOT EXISTS employee (
    emp_id IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    age INT NOT NULL,
    email varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee_department (
    id IDENTITY PRIMARY KEY,
    emp_id INT NOT NULL,
    dep_id INT NOT NULL
);
