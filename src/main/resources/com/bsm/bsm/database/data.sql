use book_store_db;

-- insert data for table user
insert user (id, name, email, password, dob, telephone) values ('11115678', 'Minh Thu', 'thu.admin@bms.com', '$2a$12$4USYbZyH0x77oZgTd2Oji.C3aJXgkb3K8PmYP3o47TUS6I3Vt03wG
', '2003-06-01', '0101012345'); -- password: 01062003
    insert user (id, name, email, password, dob, telephone) values ('11115679', 'Hoang Kha', 'kha.admin@bms.com', '$2a$12$vdz7/qrIG4Fb1Z5YHzYEX.5sImzChCKjr6Sb88dEpTZCqZcJhb1Gi
', '2003-11-11', '0101012346'); -- password: 11112003
insert user (id, name, email, password, dob, telephone) values ('22225678', 'Bao Khanh', 'khanh.employee@bms.com', '$2a$12$bHxUp74tZNStRJCAi0PyE.5/NpP5Ay0z.UozP8Me2V/LgToy8B1DW', '2003-09-12', '0101012347'); -- password: 12092003
insert user (id, name, email, password, dob, telephone) values ('22225679', 'Minh Triet', 'triet.employee@bms.com', '$2a$12$ZiOQq1mZ5kRkPHjYXoBpNO.Xmw4jnykkEpKD/2qtP51DiYQM2fxpC', '2003-10-10', '0101012348'); -- password: 10102003
select * from  publisher;

-- insert data for table admin
set @adminID1 = (select id from user where email = 'thu.admin@bms.com');
insert admin (userId) values(@adminID1);
set @adminID2 = (select id from user where email = 'kha.admin@bms.com');
insert admin (userId) values(@adminID2);

-- insert data for table employee
set @employeeID1 = (select id from user where email = 'khanh.employee@bms.com');
insert employee (userId) values(@employeeID1);
set @employeeID2 = (select id from user where email = 'triet.employee@bms.com');
insert employee (userId) values(@employeeID2);
select * from author;

-- insert data for table author
insert into author (id, name, introduction) values ('33331111', 'Nguyen Nhat Anh', 'Nguyen Nhat Anh was born on May 7, 1955 in Hue. He is a Vietnamese writer, journalist, and playwright. He is the author of many famous works such as: Toi thay hoa vang tren co xanh, Cho toi xin mot ve di tuoi tho, ...');
insert into author (name, introduction) values ('Nguyen Ngoc Tu', 'Nguyen Ngoc Tu was born on May 7, 1955 in Hue. She is a Vietnamese writer, journalist, and playwright. She is the author of many famous works such as: Giao thua, Nhung dua con cua lang, ...');
insert into author (name, introduction) values ('Xuan Dieu', 'Xuan Dieu was born on October 2, 1916 in Hanoi. He is a Vietnamese poet. He is the author of many famous works such as: Nhung bai tho hay nhat, Nhung bai tho hay nhat, ...');
insert into author (name, introduction) values ('Nguyen Du', 'Nguyen Du was born on January 3, 1765 in Thang Long, Hanoi. He is a Vietnamese poet. He is the author of many famous works such as: The Tale of Kieu, ...');
insert into author (name, introduction) values ('Nguyen Dinh Chieu', 'Nguyen Dinh Chieu was born on 1822 in Binh Dinh. He is a Vietnamese poet. He is the author of many famous works such as: Luc Van Tien, ...');
insert into author (name, introduction) values ('Thach Lam', 'Thach Lam was born on 1909 in Hanoi. He is a Vietnamese writer, journalist, and playwright. He is the author of many famous works such as: Dang Thuy Tram, ...');
insert into author (name, introduction) values ('Nguyen Huy Thiep', 'Nguyen Huy Thiep was born on October 20, 1950 in Hanoi. He is a Vietnamese writer, journalist, and playwright. He is the author of many famous works such as: The General Retires, The General Retires, ...');
insert into author (name, introduction) values ('Nguyen Quang Sang', 'Nguyen Quang Sang was born on 1969 in Hanoi. He is a Vietnamese writer, journalist, and playwright. He is the author of many famous works such as: The General Retires, The General Retires, ...');
insert into author (name, introduction) values ('Gosho Aoyama', 'Gosho Aoyama was born on June 21, 1963 in Hokuei, Tottori, Japan. He is a Japanese manga artist. He is the author of many famous works such as: Detective Conan, Magic Kaito, ...');
insert into author (name, introduction) values ('J.K. Rowling', 'J.K. Rowling was born on July 31, 1965 in Yate, Gloucestershire, England. She is a British author, philanthropist, film producer, television producer, and screenwriter. She is the author of many famous works such as: Harry Potter series, The Casual Vacancy, ...');
insert into author (name, introduction) values ('Edgar Allan Poe', 'Edgar Allan Poe was born on January 19, 1809 in Boston, Massachusetts, U.S. He is an American writer, poet, editor, and literary critic. He is the author of many famous works such as: The Tell-Tale Heart, The Raven, ...');
insert into author (name, introduction) values ('Jane Austen', 'Jane Austen was born on December 16, 1775 in Steventon, Hampshire, England. She is an English novelist known primarily for her six major novels, which interpret, critique and comment upon the British landed gentry at the end of the 18th century. Her most famous works are: Pride and Prejudice, Sense and Sensibility, ...');
insert into author (name, introduction) values ('Hilary Mantel', 'Hilary Mantel was born on July 6, 1952 in Glossop, Derbyshire, England. She is an English writer whose work includes personal memoirs, short stories, and historical fiction. She is the author of many famous works such as: Wolf Hall, Bring Up the Bodies, ...');
insert into author (name, introduction) values ('John Dewey', 'John Dewey was born on October 20, 1859 in Burlington, Vermont, U.S. He was an American philosopher, psychologist, and educational reformer whose ideas have been influential in education and social reform. He is the author of many famous works such as: Experience and Education, Democracy and Education, ...');
insert into author (name, introduction) values ('Daniel Kahneman', 'Daniel Kahneman was born on March 5');
insert into author (name, introduction) values ('Neil deGrasse Tyson', 'Neil deGrasse Tyson was born on October 5, 1958 in New York City, U.S. He is an American astrophysicist, planetary scientist, author, and science communicator. He is the author of many famous works such as: Astrophysics for People in a Hurry, Death by Black Hole, ...');
insert into author (name, introduction) values ('Noam Chomsky', 'Noam Chomsky was born on December 7, 1928 in Philadelphia, Pennsylvania, U.S. He is an American linguist, philosopher, cognitive scientist, historian, social critic, and political activist. He is the author of many famous works such as: Manufacturing Consent, Hegemony or Survival, ...');
insert into author (name, introduction) values ('Pablo Picasso', 'Pablo Picasso was born on October 25, 1881 in Malaga, Spain. He was a Spanish painter, sculptor, printmaker, ceramicist, and stage designer. He is the author of many famous works such as: Guern');
insert into author (name, introduction) values ('Tony Robbins', 'Tony Robbins was born on February 29, 1960 in North Hollywood, California, U.S. He is an American author, coach, motivational speaker, and philanthropist. He is the author of many famous works such as: Awaken the Giant Within, Unlimited Power, ...'); 
select * from author;

-- insert data for table publisher
insert into publisher (id, name, address) values ('44441111', 'Nha Nam', '59/61/63 Vo Van Tan, Ward 6, District 3, Ho Chi Minh City');
insert into publisher (name, address) values ('Kim Dong', '12 Hoa Ma, Phuc Xa Ward, Ba Dinh District, Hanoi');
insert into publisher (name, address) values ('Nha Xuat Ban Tre', '36 Tran Huy Lieu, Ward 12, Phu Nhuan District, Ho Chi Minh City');
insert into publisher (name, address) values ('NXB Thanh Nien', '161 Ba Trieu, Le Dai Hanh Ward, Hai Ba Trung District, Hanoi');
insert into publisher (name, address) values ('NXB Tong Hop TPHCM', '53 Vo Van Tan, Ward 6, District 3, Ho Chi Minh City');
insert into publisher (name, address) values ('NXB Phu Nu', '61/11/1 Tran Quoc Toan, Ward 8, District 3, Ho Chi Minh City');
insert into publisher (name, address) values ('NXB Ho Chi Minh', '62 Nguyen Thi Minh Khai, Ben Nghe Ward, District 1, Ho Chi Minh City');
insert into publisher (name, address) values ('NXB Kim Dong', '12 Hoa Ma, Phuc Xa Ward, Ba Dinh District, Hanoi');
insert into publisher (name, address) values ('NXB Hanoi', '46 Trang Tien, Trang Tien Ward, Hoan Kiem District, Hanoi');
insert into publisher (name, address) values ('NXB Can Tho', '53 Vo Van Tan, Ward 6, District 3, Ho Chi Minh City');
select * from publisher;

-- insert data for table category
insert into category (id, name, description) values ('55551111', 'Short Stories', 'Short stories are brief works of fiction. They are often published in literary magazines, anthologies, or as a collection of short stories.');
insert into category (name, description) values ('Comics', 'Comics is a medium used to express ideas by images, often combined with text or other visual information.');
insert into category (name, description) values ('Novel', 'A novel is a relatively long work of narrative fiction, normally written in prose form, and which is typically published as a book.');
insert into category (name, description) values ('Horror', 'Horror is a genre of speculative fiction which is intended to frighten, scare, or disgust.');
insert into category (name, description) values ('Detective', 'Detective fiction is a subgenre of crime fiction and mystery fiction in which an investigator or a detective—either professional, amateur or retired—investigates a crime');
insert into category (name, description) values ('History', 'History is the study of the past. Events occurring before the invention of writing systems are considered prehistory.');
insert into category (name, description) values ('Education', 'Education is the process of facilitating learning, or the acquisition of knowledge, skills, values, morals, beliefs, and habits.');
insert into category (name, description) values ('Thinking', 'Thinking is the cognitive activities you use to process information, solve problems, make decisions, and create new ideas.');
insert into category (name, description) values ('Science', 'Science is a systematic enterprise that builds and organizes knowledge in the form of testable explanations and predictions about the universe.');
insert into category (name, description) values ('Art', 'Art is a diverse range of human activities and the products of those activities.');
insert into category (name, description) values ('Personal Development', 'Personal development covers activities that improve awareness and identity, develop talents and potential, build human capital and facilitate employability, enhance the quality of life and contribute to the realization of dreams and aspirations.');
select * from category;

-- insert data for table book
select id from publisher where name = 'Nha Xuat Ban Tre';
set @publisherID = (select id from publisher where name = 'Nha Xuat Ban Tre');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1234567890123', 'Toi thay hoa vang tren co xanh', @publisherID, '2015-01-01', 'Tieng Viet');
set @authorID = (select id from author where name = 'Nguyen Nhat Anh');
insert into bookAuthor (bookID, authorID) values ('1234567890123', @authorID);

set @authorID = (select id from author where name = 'J.K. Rowling');
set @publisherID = (select id from publisher where name = 'Nha Xuat Ban Tre');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1234361290124', 'Harry Potter Va Chiec Coc Lua', @publisherID, '2018-01-01', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1234361290124', @authorID);

set @authorID = (select id from author where name = 'Nguyen Ngoc Tu');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067320125', 'Giao thua', @publisherID, '2020-01-01', 'Tieng Viet');
insert into bookAuthor (bookID, authorID) values ('1034067320125', @authorID);

set @authorID = (select id from author where name = 'Gosho Aoyama');
set @publisherID = (select id from publisher where name = 'Kim Dong');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067326126', 'Conan Tap 12', @publisherID, '2019-01-01', 'Tieng Viet');
insert into bookAuthor (bookID, authorID) values ('1034067326126', @authorID);

set @authorID = (select id from author where name = 'Edgar Allan Poe');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067321127', 'The Tell-Tale Heart', @publisherID, '2020-04-12', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1034067321127', @authorID);

set @authorID = (select id from author where name = 'Jane Austen');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067323130', 'Pride and Prejudice', @publisherID, '2019-10-21', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1034067323130', @authorID);

set @authorID = (select id from author where name = 'Hilary Mantel');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067323133', 'Wolf Hall', @publisherID, '2020-11-11', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1034067323133', @authorID);

set @authorID = (select id from author where name = 'John Dewey');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067324255', 'Experience and Education', @publisherID, '2017-04-13', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1034067324255', @authorID);

set @authorID = (select id from author where name = 'Daniel Kahneman');
set @publisherID = (select id from publisher where name = 'Nha Nam');
insert into book (isbn, title, publisherID, publishingDate, languages)
values ('1034067325666', 'Thinking, Fast and Slow', @publisherID, '2020-06-07', 'Tieng Anh');
insert into bookAuthor (bookID, authorID) values ('1034067325666', @authorID);

select * from book;


set @adminID1 = (select a.id from user u join admin a on u.id = a.userID where email = 'thu.admin@bms.com');
set @adminID2 = (select a.id from user u join admin a on u.id = a.userID where email = 'kha.admin@bms.com');
set @employeeID1 = (select e.id from user u join employee e on u.id = e.userID where email = 'khanh.employee@bms.com');
set @employeeID2 = (select e.id from user u join employee e on u.id = e.userID where email = 'triet.employee@bms.com');
-- insert data for table importSheet
insert into importSheet (employeeID) values (@employeeID1);

-- insert data for table bookCategory and bookBatch and update quantity in table book
set @importSheetID = (select id from importSheet where employeeID = @employeeID1 and importDate = CURRENT_DATE());

set @bookID = (select isbn from book where title = 'Toi thay hoa vang tren co xanh');
set @categoryID = (select id from category where name = 'Short Stories');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 70000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 70000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 70000 * 1.2 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Harry Potter Va Chiec Coc Lua');
set @categoryID = (select id from category where name = 'Novel');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 200000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 200000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 200000 * 1.4 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Giao thua');
set @categoryID = (select id from category where name = 'Short Stories');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 300, 55000, @importSheetID);
update importSheet set quantity = quantity + 300 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 55000 * 300 where id = @importSheetID;
update book set quantity = quantity + 300 where isbn = @bookID;
update book set salePrice = 55000 * 1.12 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Conan Tap 12');
set @categoryID = (select id from category where name = 'Comics');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 15000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 15000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 15000 * 1.3 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'The Tell-Tale Heart');
set @categoryID = (select id from category where name = 'Horror');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 30000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 30000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 30000 * 1.25 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Pride and Prejudice');
set @categoryID = (select id from category where name = 'Novel');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 150, 80000, @importSheetID);
update importSheet set quantity = quantity + 150 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 80000 * 150 where id = @importSheetID;
update book set quantity = quantity + 150 where isbn = @bookID;
update book set salePrice = 80000 * 1.25 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Wolf Hall');
set @categoryID = (select id from category where name = 'History');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 100, 100000, @importSheetID);
update importSheet set quantity = quantity + 100 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 100000 * 100 where id = @importSheetID;
update book set quantity = quantity + 100 where isbn = @bookID;
update book set salePrice = 100000 * 1.2 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Experience and Education');
set @categoryID = (select id from category where name = 'Education');
insert into bookCategory (bookID, categoryID) values (@bookID, @categoryID);
insert into bookBatch(id, quantity, importPrice, importSheetID) values (@bookID, 200, 120000, @importSheetID);
update importSheet set quantity = quantity + 200 where id = @importSheetID;
update importSheet set totalPrice = totalPrice + 120000 * 200 where id = @importSheetID;
update book set quantity = quantity + 200 where isbn = @bookID;
update book set salePrice = 120000 * 1.4 where isbn = @bookID;

set @bookID = (select isbn from book where title = 'Thinking, Fast and Slow');
set @categoryID = (select id from category where name = 'Thinking');
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
insert into customer (id) values ('1');

insert into customer (name, phone) values ('Phuong', '0917527783');
insert into customer (name, phone) values ('Huong', '0903032321');
insert into customer (name, phone) values ('Thao', '0123456789');
select * from customer;

-- insert data for table orderSheet
set @customerID = (select id from customer where name = 'Anonymous');
insert into orderSheet (employeeID, customerID) values (@employeeID2, @customerID);

select * from orderSheet;

-- insert data for table orderBooksDetails
set @orderID = (
    select id
    from orderSheet
    where employeeID = @employeeID2 and customerID = @customerID and orderDate = CURRENT_DATE()
);
set @bookID = (select isbn from book where title = 'Toi thay hoa vang tren co xanh');
set @salePrice = (select importPrice from bookBatch where id = @bookID) * 1.2;
insert into orderBooksDetails(orderID, bookID, importSheetID, quantity, salePrice)
values (@orderID, @bookID, @importSheetID, 1, @salePrice);
update book set quantity = quantity - 1 where isbn = @bookID;

set @orderID = (
    select id
    from orderSheet
    where employeeID = @employeeID2 and customerID = @customerID and orderDate = current_date()
);
set @bookID = (select isbn from book where title = 'Harry Potter Va Chiec Coc Lua');
set @salePrice = (select importPrice from bookBatch where id = @bookID) * 1.2;
insert into orderBooksDetails(orderID, bookID, importSheetID,  quantity, salePrice)
values (@orderID, @bookID, @importSheetID, 1, @salePrice);
update book set quantity = quantity - 1 where isbn = @bookID;

select * from orderBooksDetails;
select * from author;