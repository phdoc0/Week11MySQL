CREATE DATABASE IF NOT EXISTS Liquor;
USE Liquor;
DROP TABLE IF EXISTS Liquor;
CREATE TABLE Liquor(
	id int(10) NOT NULL AUTO_INCREMENT,
	name varchar(60) NOT NULL,
	category varchar(60) NOT NULL,
	quantity varchar(60),
	price DECIMAL(19,4),
	PRIMARY key(id)
);

