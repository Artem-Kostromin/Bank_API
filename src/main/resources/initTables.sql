drop table if exists clients;
drop table if exists account;
drop table if exists cards;

create table clients (
id int (10) AUTO_INCREMENT NOT NULL,
login varchar(20) NOT NULL,
password varchar(100) NOT NULL,
PRIMARY KEY (id)
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

INSERT into clients values (1, 'Dmitriy', 'root'), (2, 'Artem', 'moot'), (3, 'Ivan', 'toot');