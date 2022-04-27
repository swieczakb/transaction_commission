CREATE TABLE TRANSACTION (
     id int auto_increment,
     amount varchar(255),
     currency varchar(255),
     execution_date date,
     client_id int
);