create table clients (
id int (10) AUTO_INCREMENT NOT NULL,
name varchar(20) NOT NULL,
login varchar(20) NOT NULL,
password varchar(100) NOT NULL,
PRIMARY KEY (id_user)
);
create table account (
id int (10) AUTO_INCREMENT NOT NULL,
client_id int (10) NOT NULL,
PRIMARY KEY (id)
);
create table cards (
id int (10) AUTO_INCREMENT NOT NULL,
account_id int (10) NOT NULL,
balance int(11),
PRIMARY KEY (id)
);

INSERT into clients values ('Dmitriy', '1', 'q'), ('Artem', '2', 'w'), ('Ivan', '3', 'e');