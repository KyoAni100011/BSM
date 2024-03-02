-- drop database if exists book_store_db;
create database book_store_db;
use book_store_db;

create table user (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    name nvarchar(255) not null,
    email nvarchar(255) not null,
    password nvarchar(255) not null,
    dob date not null,
    isEnabled boolean default true,
    createdAt datetime default current_timestamp(),
    lastLogin datetime default current_timestamp()
);

create table admin (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    userID binary(16) not null,
    foreign key (userID) references user(id)
);

create table employee (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    userID binary(16) not null,
    foreign key (userID) references user(id)
);

create table author (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    name nvarchar(255) not null,
    isEnabled boolean default true
);

create table publisher (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    name nvarchar(255) not null,
    isEnabled boolean default true
);

create table category (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    name nvarchar(255) not null,
    isEnabled boolean default true
);

create table book (
    isbn varchar(13) not null primary key,
    title nvarchar(255) not null,
    publisherID binary(16) not null,
    authorID binary(16) not null,
    publishingDate date not null,
    languages nvarchar(255) not null,
    isEnabled boolean default true,
    quantity int not null default 0,
    salePrice decimal(50, 2) not null default 0.0,
    foreign key(publisherID) references publisher(id),
    foreign key(authorID) references author(id)
);

create table bookCategory (
    bookID varchar(13) not null,
    categoryID binary(16) not null,
    primary key (bookID, categoryID),
    foreign key(bookID) references book(isbn),
    foreign key(categoryID) references category(id)
);

create table importSheet (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    employeeID binary(16) not null,
    importDate date not null default (CURRENT_DATE),
    quantity int not null default 0,
    totalPrice decimal(50, 2) not null default 0.0,
    foreign key(employeeID) references employee(id)
);


create table bookBatch (
    id varchar(13) not null,
    quantity int not null,
    importPrice decimal(50, 2) not null,
    importSheetID binary(16) not null,
    primary key (id, importSheetId),
    foreign key(id) references book(isbn),
    foreign key (importSheetID) references importSheet(id)
);

create table customer (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    name nvarchar(255) not null default 'Anonymous',
    phone nvarchar(12) not null default '0000000000'
);

create table orderSheet (
    id binary(16) default (uuid_to_bin(uuid())) primary key,
    employeeID binary(16) not null,
    customerID binary(16) not null,
    orderDate date not null default (CURRENT_DATE),
    totalPrice decimal(50, 2) not null default 0.0,
    foreign key(employeeID) references employee(id),
    foreign key(customerID) references customer(id)
);

create table orderBooksDetails (
    orderID binary(16) not null,
    bookID varchar(13) not null,
    importSheetID binary(16),
    quantity int not null,
    salePrice decimal(50, 2) not null,
    primary key (orderID, bookID),
    foreign key(orderID) references orderSheet(id),
    foreign key (bookID, importSheetID) references bookBatch(id, importSheetID)
);