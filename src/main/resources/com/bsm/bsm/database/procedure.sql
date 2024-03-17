use book_store_db;

DROP PROCEDURE IF EXISTS ADDUSER;

DELIMITER //

CREATE PROCEDURE ADDUSER(
    IN name_param VARCHAR(255),
    IN dob_param DATE,
    IN email_param VARCHAR(255),
    IN password_param VARCHAR(255),
    IN role_param VARCHAR(255)
)
BEGIN
INSERT INTO user (name, dob, email, password)
VALUES (name_param, dob_param, email_param, password_param);
set @user_id = last_insert_id();
    if role_param = 'admin' then
		insert into admin(userID) values (@user_id);
else
		insert into employee(userID) values (@user_id);
end if;
END //

DELIMITER ;

CALL ADDUSER('thu', '1999-01-01', 'thunguyen.admin@bms.com', '123456', 'admin');

