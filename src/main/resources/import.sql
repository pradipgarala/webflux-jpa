insert into department (name, description) values ('admin', 'admin department');
insert into department (name, description) values ('engineering', 'engineering department');

insert into employee (name, age, email) values ('alice', 10, 'alice@test.com');
insert into employee (name, age, email) values ('bob', 20, 'bob@test.com');
insert into employee (name, age, email) values ('charlie', 30, 'charlie@test.com');

insert into employee_department (emp_id, dep_id) values (1, 1);
insert into employee_department (emp_id, dep_id) values (2, 2);
insert into employee_department (emp_id, dep_id) values (3, 1);
insert into employee_department (emp_id, dep_id) values (3, 2);