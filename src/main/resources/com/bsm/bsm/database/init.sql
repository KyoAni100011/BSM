drop database if exists book_store_db;
create database book_store_db;
use book_store_db;

create table user (
    id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    dob date not null,
    telephone char(10) unique,
    address char(255),
    isEnabled boolean default true,
    createdAt datetime default current_timestamp(),
    lastLogin datetime
);

create table admin (
    id int auto_increment primary key,
    userID int not null,
    foreign key (userID) references user(id)
);

create table employee (
    id int auto_increment primary key,
    userID int not null,
    foreign key (userID) references user(id)
);

create table author (
    id int auto_increment primary key,
    name varchar(255) unique not null,
    introduction text,
    isEnabled boolean default true
);

create table publisher (
    id int auto_increment primary key,
    name varchar(255) unique not null,
    address varchar(255) not null,
    isEnabled boolean default true
);

create table category (
    id int auto_increment primary key,
    name varchar(255) unique not null,
    description text,
    isEnabled boolean default true
);

create table language (
    name varchar(255) primary key
);

create table book (
    isbn int auto_increment primary key,
    title  varchar(255) unique,
    publisherID int not null,
    publishingDate date not null,
    language varchar(255) not null,
    isEnabled boolean default true,
    quantity int not null default 0,
    salePrice decimal(50, 0) not null default 0.0,
    foreign key(publisherID) references publisher(id),
    foreign key(language) references language(name)
);

create table bookCategory (
    bookID int not null,
    categoryID int not null,
    primary key (bookID, categoryID),
    foreign key(bookID) references book(isbn),
    foreign key(categoryID) references category(id)
);

create table bookAuthor (
    bookId int not null,
    authorID int not null,
    primary key (bookID, authorID),
    foreign key(bookID) references book(isbn),
    foreign key(authorID) references author(id)
);

select * from bookAuthor;

create table importSheet (
    id int auto_increment primary key,
    employeeID int not null,
    importDate date not null default (CURRENT_DATE),
    quantity int not null default 0,
    totalPrice decimal(50, 0) not null default 0.0,
    foreign key(employeeID) references employee(id)
);

create table bookBatch (
    id int auto_increment primary key,
    quantity int not null,
    importPrice decimal(50, 0) not null,
    importSheetID int not null,
    bookID int not null,
    foreign key(bookID) references book(isbn),
    foreign key (importSheetId) references importSheet(id)
);

create table customer (
    id int auto_increment primary key,
    name varchar(255) not null default 'Anonymous',
    phone varchar(11) not null default '0000000000'
);

create table orderSheet (
    id int auto_increment primary key,
    employeeID int not null,
    customerID int not null,
    orderDate date not null default (CURRENT_DATE),
    totalPrice decimal(50, 0) not null default 0.0,
    foreign key(employeeID) references employee(id),
    foreign key(customerID) references customer(id)
);

create table orderBooksDetails (
    orderID int not null,
    bookBatchID int not null,
    quantity int not null,
    salePrice decimal(50, 0) not null,
    primary key (orderID, bookBatchID),
    foreign key(orderID) references orderSheet(id),
    foreign key (bookBatchID) references bookBatch(id)
);


