INSERT INTO letter_template (name, subject, body)
VALUES ('EMPLOYEE_HIRED', 'Сообщение от компании', '{{model[''employee''].fullName}}, вы наняты в компанию Рога & Копыта');

INSERT INTO letter_template (name, subject, body)
VALUES ('EMPLOYEE_FIRED', 'Сообщение от компании', '{{model[''employee''].fullName}}, вы уволены из компании Рога & Копыта');

INSERT INTO employee (email, last_name, first_name, middle_name, status)
VALUES ('johndoe@example.com', 'Doe', 'John', null, 'HIRED');