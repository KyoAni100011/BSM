use book_store_db;

select * from bookCategory;

DROP PROCEDURE IF EXISTS ADDUSER;

DELIMITER //

CREATE PROCEDURE ADDUSER(
    IN name_param VARCHAR(255),
    IN dob_param DATE,
    IN email_param VARCHAR(255),
    IN address_param VARCHAR(255),
    IN password_param VARCHAR(255),
    IN role_param VARCHAR(255)
)
BEGIN
    DECLARE user_count INT;
    DECLARE user_id INT;
    DECLARE id_prefix VARCHAR(10);

    IF role_param = 'admin' THEN
        SET id_prefix = '11110000';
SELECT COUNT(*) INTO user_count FROM admin;
ELSE
        SET id_prefix = '22220000';
SELECT COUNT(*) INTO user_count FROM employee;
END IF;

    SET user_id = user_count + 1;
    SET @id = id_prefix + CAST(user_id AS CHAR);

INSERT INTO user (id, name, dob, email, address, password)
VALUES (@id, name_param, dob_param, email_param, address_param, password_param);

IF role_param = 'admin' THEN
        INSERT INTO admin (userID) VALUES (@id);
ELSE
        INSERT INTO employee (userID) VALUES (@id);
END IF;
END; //

DELIMITER ;

-----------------------------------------------------------------------------------


DROP PROCEDURE IF EXISTS CreateImportSheets;

DELIMITER //
CREATE PROCEDURE CreateImportSheets()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE j INT DEFAULT 0;
    DECLARE importSheetID INT;

    WHILE i < 10 DO
        -- Tạo đơn nhập sách mới
        SET @employeeID = (SELECT id from employee ORDER BY RAND() LIMIT 1);
INSERT INTO importSheet (employeeID, importDate) VALUES (@employeeID, CURRENT_DATE());
SET importSheetID = LAST_INSERT_ID();

        WHILE j < 5 DO
            -- Chọn ngẫu nhiên một cuốn sách từ danh sách có sẵn
            SET @bookID = (SELECT isbn FROM book ORDER BY RAND() LIMIT 1);
            -- Chọn ngẫu nhiên một giá nhập và số lượng sách
            SET @quantity = FLOOR(RAND() * 200) + 1; -- Số lượng từ 1 đến 200
            SET @importPrice = FLOOR(RAND() * 100000) + 50000;

            -- Thêm lô sách vào đơn nhập sách
INSERT INTO bookBatch (quantity, importPrice, importSheetID, bookID)
VALUES (@quantity, @importPrice, importSheetID, @bookID);

-- Cập nhật tổng số lượng và giá trị của đơn nhập sách
UPDATE importSheet
SET quantity = quantity + @quantity,
    totalPrice = totalPrice + (@importPrice * @quantity)
WHERE id = importSheetID;

-- Cập nhật số lượng sách trong kho
UPDATE book
SET quantity = quantity + @quantity
WHERE isbn = @bookID;

-- Cập nhật lại giá bán sách
SET @maxImportPrice = (SELECT DISTINCT MAX(importPrice) FROM bookBatch WHERE bookID = @bookID);

UPDATE book
SET salePrice = @maxImportPrice * 1.1
WHERE isbn = @bookID;

SET j = j + 1;
END WHILE;

        SET j = 0;
        SET i = i + 1;
END WHILE;
END;

call CreateImportSheets();
DELIMITER ;

-----------------------------------------------------------------------------------

DROP PROCEDURE IF EXISTS CREATEORDERS;

DELIMITER //

CREATE PROCEDURE CREATEORDERS()
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE j INT DEFAULT 0;
    DECLARE orderID INT;

    WHILE i < 10 DO
        SET @employeeID = (SELECT id FROM employee ORDER BY RAND() LIMIT 1);
        SET @customerID = (SELECT id FROM customer ORDER BY RAND() LIMIT 1);
        SET @orderDate = (SELECT DATE_FORMAT(
							DATE_ADD(
								'2024-01-01',
								INTERVAL FLOOR(RAND() * DATEDIFF(NOW(), '2024-01-01')) DAY
							), '%Y-%m-%d'));

        INSERT INTO orderSheet (employeeID, customerID, orderDate) VALUES (@employeeID, @customerID, @orderDate);
        SET @orderID = LAST_INSERT_ID();

        WHILE j < 3 DO
            SET @bookID = (SELECT ISBN FROM BOOK ORDER BY RAND() LIMIT 1);
            SET @quantityInput = 2;
            SET @qtyBook = (SELECT quantity FROM book WHERE isbn = @bookID);
            SET @salePrice = (SELECT salePrice FROM book WHERE isbn = @bookID);

            IF @qtyBook >= @quantityInput THEN
                WHILE @quantityInput != 0 DO
                    SET @qtyFromBookBatch = (SELECT bb.quantity
                                             FROM bookBatch bb
                                             JOIN importsheet i ON bb.importSheetID = i.id
                                             WHERE bb.quantity > @quantityInput
                                             AND bookID = @bookID
                                             ORDER BY importDate
                                             LIMIT 1);
                    SET @bookBatchID = (SELECT bb.id
                                        FROM bookBatch bb
                                        JOIN importsheet i ON bb.importSheetID = i.id
                                        WHERE bb.quantity > @quantityInput
                                        AND bookID = @bookID
                                        ORDER BY importDate
                                        LIMIT 1);

                    IF @qtyFromBookBatch >= @quantityInput THEN
                        INSERT IGNORE INTO orderBooksDetails (orderID, bookBatchID, quantity, salePrice)
                        VALUES (@orderID, @bookBatchID, @quantityInput, @salePrice);
                        UPDATE bookBatch SET quantity = quantity - @quantityInput WHERE id = @bookBatchID;
                        UPDATE book SET quantity = quantity - @quantityInput WHERE isbn = @bookID;
                        UPDATE orderSheet SET totalPrice = totalPrice + @salePrice * @quantityInput WHERE id = @orderID;
                        SET @quantityInput = 0;
                    ELSE
                        INSERT IGNORE INTO orderBooksDetails (orderID, bookBatchID, quantity, salePrice)
                        VALUES (@orderID, @bookBatchID, @qtyFromBookBatch, @salePrice);
                        UPDATE bookBatch SET quantity = 0 WHERE id = @bookBatchID;
                        UPDATE book SET quantity = quantity - @qtyFromBookBatch WHERE isbn = @bookID;
                        UPDATE orderSheet SET totalPrice = totalPrice + @salePrice * @qtyFromBookBatch WHERE id = @orderID;
                        SET @quantityInput = @quantityInput - @qtyFromBookBatch;
                    END IF;
                END WHILE;
            END IF;
            SET j = j + 1;
        END WHILE;
        SET j = 0;
        SET i = i + 1;
    END WHILE;
END//

DELIMITER ;

CALL CREATEORDERS();
DELIMITER ;


-- select * from orderSheet o join orderBooksDetails i on o.id = i.orderID;
