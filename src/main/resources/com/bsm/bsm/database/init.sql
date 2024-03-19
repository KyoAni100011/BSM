-- drop database if exists book_store_db;
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
    lastLogin datetime default current_timestamp()
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
    name varchar(255) not null,
    introduction text,
    isEnabled boolean default true
);

create table publisher (
    id int auto_increment primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    isEnabled boolean default true
);

create table category (
    id int auto_increment primary key,
    name varchar(255) not null,
    description text,
    isEnabled boolean default true
);

create table book (
    isbn bigint auto_increment primary key,
    title varchar(255) not null,
    publisherID int not null,
    publishingDate date not null,
    languages varchar(255) not null,
    isEnabled boolean default true,
    quantity int not null default 0,
    salePrice decimal(50, 2) not null default 0.0,
    foreign key(publisherID) references publisher(id)
);

create table bookCategory (
    bookID bigint not null,
    categoryID int not null,
    primary key (bookID, categoryID),
    foreign key(bookID) references book(isbn),
    foreign key(categoryID) references category(id)
);

create table bookAuthor (
    bookId bigint not null,
    authorID int not null,
    primary key (bookID, authorID),
    foreign key(bookID) references book(isbn),
    foreign key(authorID) references author(id)
);

create table importSheet (
    id bigint auto_increment primary key,
    employeeID int not null,
    importDate date not null default (CURRENT_DATE),
    quantity int not null default 0,
    totalPrice decimal(50, 2) not null default 0.0,
    foreign key(employeeID) references employee(id)
);

create table bookBatch (
    id bigint not null,
    quantity int not null,
    importPrice decimal(50, 2) not null,
    importSheetID bigint not null,
    primary key (id, importSheetId),
    foreign key(id) references book(isbn),
    foreign key (importSheetId) references importSheet(id)
);

create table customer (
    id int auto_increment primary key,
    name varchar(255) not null default 'Anonymous',
    phone varchar(12) not null default '0000000000'
);

create table orderSheet (
    id bigint auto_increment primary key,
    employeeID int not null,
    customerID int not null,
    orderDate date not null default (CURRENT_DATE),
    totalPrice decimal(50, 2) not null default 0.0,
    foreign key(employeeID) references employee(id),
    foreign key(customerID) references customer(id)
);

create table orderBooksDetails (
    orderID bigint not null,
    bookID bigint not null,
    importSheetID bigint,
    quantity int not null,
    salePrice decimal(50, 2) not null,
    primary key (orderID, bookID),
    foreign key(orderID) references orderSheet(id),
    foreign key (bookID, importSheetID) references bookBatch(id, importSheetID)
);