drop table if exists clients;
drop table if exists accounts;
drop table if exists cards;

create table accounts (
id IDENTITY NOT NULL,
PRIMARY KEY (id)
);
create table cards (
id IDENTITY NOT NULL,
account_id int (10) NOT NULL,
balance int(11),
PRIMARY KEY (id)
);

INSERT into accounts values (default), (default), (default);
INSERT into cards values (default, 1, 500), (default, 2, 789), (default, 3, 1243);