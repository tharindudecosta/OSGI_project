create database pcstoredb;
USE pcstoredb;

create table customer(
customerId int NOT NULL AUTO_INCREMENT,
firstName VARCHAR(50) ,
lastName VARCHAR(50) ,
email VARCHAR(70) ,
phone VARCHAR(10) ,
password VARCHAR(50) ,
isActive BOOLEAN,
PRIMARY KEY (customerId)
);

create table employee(
employeeId int NOT NULL AUTO_INCREMENT,
firstName VARCHAR(50) ,
lastName VARCHAR(50) ,
email VARCHAR(70) ,
phone VARCHAR(10) ,
isActive BOOLEAN,
PRIMARY KEY (employeeId)
);

create table product(
productId int NOT NULL AUTO_INCREMENT,
productName VARCHAR(50) ,
brand VARCHAR(50) ,
unitPrice VARCHAR(10) ,
quantity VARCHAR(20) ,
isActive BOOLEAN,
PRIMARY KEY (productid)
);

create table productOrders(
orderId int NOT NULL AUTO_INCREMENT,
productId int ,
productName VARCHAR(50) ,
deliveryaddress VARCHAR(50) ,
price VARCHAR(10) ,
isActive BOOLEAN,
PRIMARY KEY (orderid)
);

create table supplier(
supplierId int NOT NULL AUTO_INCREMENT,
firstName VARCHAR(50),
lastName VARCHAR(50),
email VARCHAR(60),
nic VARCHAR(20),
phone VARCHAR(20),
companyName VARCHAR(50),
isActive BOOLEAN,
PRIMARY KEY (supplierId)
);

create table stockOrder(
stockOrderId int NOT NULL AUTO_INCREMENT,
noOfStocks VARCHAR(10),
productName VARCHAR(50),
supplierCompany VARCHAR(50),
isActive BOOLEAN,
PRIMARY KEY (stockOrderId)
);


