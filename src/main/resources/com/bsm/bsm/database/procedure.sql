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
