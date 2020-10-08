TRUNCATE TABLE accounts;
TRUNCATE TABLE cards;

create table IF NOT EXISTS accounts (
id IDENTITY NOT NULL,
PRIMARY KEY (id)
);
create table IF NOT EXISTS cards (
id IDENTITY NOT NULL,
account_id int (10) NOT NULL,
balance DECIMAL(11,2),
PRIMARY KEY (id)
)