use book_store_db;

-- insert data for table user
insert user (name, email, password, dob) values ('Minh Thu', 'thu.admin@bms.com', '01062003', '2003-06-01');
insert user (name, email, password, dob) values ('Hoang Kha', 'kha.admin@bms.com', '11112003', '2003-11-11');
insert user (name, email, password, dob) values ('Bao Khanh', 'khanh.employee@bms.com', '12092003', '2003-09-12');
insert user (name, email, password, dob) values ('Minh Triet', 'triet.employee@bms.com', '10102003', '2003-10-10');
select * from user;

-- insert data for table admin
set @adminID1 = (select id from user where email = 'thu.admin@bms.com');
insert admin (userId) values(@adminID1);
set @adminID2 = (select id from user where email = 'kha.admin@bms.com');
insert admin (userId) values(@adminID2);
select bin_to_uuid(ad.id), bin_to_uuid(userID), name from admin ad join user u on ad.userId = u.id;

-- insert data for table employee
set @employeeID1 = (select id from user where email = 'khanh.employee@bms.com');
insert employee (userId) values(@employeeID1);
set @employeeID2 = (select id from user where email = 'triet.employee@bms.com');
insert employee (userId) values(@employeeID2);
select bin_to_uuid(emp.id), bin_to_uuid(userID), name from employee emp join user u on emp.userId = u.id;

-- insert data for table author
insert into author (name) values ('Nguyen Nhat Anh');
insert into author (name) values ('Nguyen Ngoc Tu');
insert into author (name) values ('Gosho Aoyama');
insert into author (name) values ('J.K. Rowling');
insert into author (name) values ('Edgar Allan Poe');
insert into author (name) values ('Jane Austen');
insert into author (name) values ('Hilary Mantel');
insert into author (name) values ('John Dewey');
insert into author (name) values ('Daniel Kahneman');
insert into author (name) values ('Neil deGrasse Tyson');
insert into author (name) values ('Noam Chomsky');
insert into author (name) values ('Pablo Picasso');
insert into author (name) values ('Tony Robbins'); -- Phat trien ban than
select * from author;

-- insert data for table publisher
insert into publisher (name) values ('Nha Nam');
insert into publisher (name) values ('Kim Dong');
insert into publisher (name) values ('Nha Xuat Ban Tre');
insert into publisher (name) values ('NXB Thanh Nien');
insert into publisher (name) values ('NXB Tong Hop TP. HCM');
insert into publisher (name) values ('NXB Phu Nu');
insert into publisher (name) values ('NXB Ho Chi Minh');
insert into publisher (name) values ('NXB Kim Dong');
insert into publisher (name) values ('NXB Ha Noi');
insert into publisher (name) values ('NXB Can Tho');
select * from publisher;

-- insert data for table category
insert into category (name) values ('Truyen ngan');
insert into category (name) values ('Truyen tranh');
insert into category (name) values ('Tieu thuyet');
insert into category (name) values ('Kinh di');
insert into category (name) values ('Trinh tham');
insert into category (name) values ('Lich su');
insert into category (name) values ('Giao duc');
insert into category (name) values ('Tu duy');
insert into category (name) values ('Khoa hoc');
insert into category (name) values ('Nghe thuat');
insert into category (name) values ('Phat trien ban than');
select * from category;

-- insert data for table book
set @authorID = (select id from author where name = 'Nguyen Nhat Anh');
set @publisherID = (select id from publisher where name = 'Nha Xuat Ban Tre');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1234567890123', 'Toi thay hoa vang tren co xanh', @publisherID, @authorID, '2015-01-01', 'Tieng Viet');

set @authorID = (select id from author where name = 'J.K. Rowling');
set @publisherID = (select id from publisher where name = 'Nha Xuat Ban Tre');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1234361290124', 'Harry Potter Va Chiec Coc Lua', @publisherID, @authorID, '2018-01-01', 'Tieng Anh');

set @authorID = (select id from author where name = 'Nguyen Ngoc Tu');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067320125', 'Giao thua', @publisherID, @authorID, '2020-01-01', 'Tieng Viet');

set @authorID = (select id from author where name = 'Gosho Aoyama');
set @publisherID = (select id from publisher where name = 'Kim Dong');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067326126', 'Conan Tap 12', @publisherID, @authorID, '2019-01-01', 'Tieng Viet');

set @authorID = (select id from author where name = 'Edgar Allan Poe');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067321127', 'The Tell-Tale Heart', @publisherID, @authorID, '2020-04-12', 'Tieng Anh');

set @authorID = (select id from author where name = 'Jane Austen');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067323130', 'Pride and Prejudice', @publisherID, @authorID, '2019-10-21', 'Tieng Anh');

set @authorID = (select id from author where name = 'Hilary Mantel');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067323133', 'Wolf Hall', @publisherID, @authorID, '2020-11-11', 'Tieng Anh');

set @authorID = (select id from author where name = 'John Dewey');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067324255', 'Experience and Education', @publisherID, @authorID, '2017-04-13', 'Tieng Anh');

set @authorID = (select id from author where name = 'Daniel Kahneman');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, authorID, publishingDate, languages)
values ('1034067325666', 'Thinking, Fast and Slow', @publisherID, @authorID, '2020-06-07', 'Tieng Anh');

select * from book;


set @adminID1 = (select a.id from user u join admin a on u.id = a.userID where email = 'thu.admin@bms.com');
set @adminID2 = (select a.id from user u join admin a on u.id = a.userID where email = 'kha.admin@bms.com');
set @employeeID1 = (select e.id from user u join employee e on u.id = e.userID where email = 'khanh.employee@bms.com');
set @employeeID2 = (select e.id from user u join employee e on u.id = e.userID where email = 'triet.employee@bms.com');
-- insert data for table importSheet
insert into importSheet (employeeID) values (@employeeID1);

-- insert data for table bookCategory and bookBatch and update quantity in table book
set @importSheetID = (select id from importSheet where employeeID = @employeeID1 and importDate = '2024-02-23');

set @bookID = (select isbn from book where title = 'Toi thay hoa vang tren co xanh');
set @categoryID = (select id from category where name = 'Truyen ngan');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 70000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 70000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 70000 * 1.2 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Harry Potter Va Chiec Coc Lua');
set @categoryID = (select id from category where name = 'Tieu thuyet');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 200000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 200000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 200000 * 1.4 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Giao thua');
set @categoryID = (select id from category where name = 'Truyen ngan');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 300, 55000, @importSheetID);
update importSheet set quantity = quantity + 300 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 55000 * 300 where id = @importSheetID;
update book set quantity = quantity + 300 where isbn = @bookID;
update book set salePrice = 55000 * 1.12 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Conan Tap 12');
set @categoryID = (select id from category where name = 'Truyen tranh');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 15000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 15000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 15000 * 1.3 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'The Tell-Tale Heart');
set @categoryID = (select id from category where name = 'Kinh di');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 30000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 30000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 30000 * 1.25 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Pride and Prejudice');
set @categoryID = (select id from category where name = 'Tieu thuyet');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 150, 80000, @importSheetID);
update importSheet set quantity = quantity + 150 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 80000 * 150 where id = @importSheetID;
update book set quantity = quantity + 150 where isbn = @bookID;
update book set salePrice = 80000 * 1.25 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Wolf Hall');
set @categoryID = (select id from category where name = 'Lich su');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 100000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 100000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 100000 * 1.2 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Experience and Education');
set @categoryID = (select id from category where name = 'Giao duc');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 120000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 120000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 120000 * 1.4 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Thinking, Fast and Slow');
set @categoryID = (select id from category where name = 'Tu duy');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 150, 150000, @importSheetID);
update importSheet set quantity = quantity + 150 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 150000 * 150 where id = @importSheetID;
update book set quantity = quantity + 150 where isbn = @bookID;
update book set salePrice = 150000 * 1.4 where isbn = @bookID;

select * from bookCategory;
select * from bookBatch;
select * from importSheet;
select * from book;

-- insert data for table customer
insert into customer (name, phone) values ('Phuong', '0917527783');
insert into customer (name, phone) values ('Huong', '0903032321');
insert into customer (name, phone) values ('Thao', '0123456789');
insert into customer (id) values (uuid_to_bin(uuid()));
select * from customer;

-- insert data for table orderSheet
set @customerID = (select id from customer where name = 'Anonymous');
insert into orderSheet (employeeID, customerID) values (@employeeID2, @customerID);

select * from orderSheet;

-- insert data for table orderBooksDetails
set @orderID = (
    select id
    from orderSheet
    where employeeID = @employeeID2 and customerID = @customerID and orderDate = '2024-02-23'
);
set @bookID = (select isbn from book where title = 'Toi thay hoa vang tren co xanh');
set @salePrice = (select importPrice from bookBatch where id = @bookID) * 1.2;
insert into orderBooksDetails(orderID, bookID, importSheetID, quantity, salePrice)
values (@orderID, @bookID, @importSheetID, 1, @salePrice);
update book set quantity = quantity - 1 where isbn = @bookID;

set @orderID = (
    select id
    from orderSheet
    where employeeID = @employeeID2 and customerID = @customerID and orderDate = '2024-02-23'
);
set @bookID = (select isbn from book where title = 'Harry Potter Va Chiec Coc Lua');
set @salePrice = (select importPrice from bookBatch where id = @bookID) * 1.2;
insert into orderBooksDetails(orderID, bookID, importSheetID,  quantity, salePrice)
values (@orderID, @bookID, @importSheetID, 1, @salePrice);
update book set quantity = quantity - 1 where isbn = @bookID;

select * from orderBooksDetails;
select * from book;
