create or replace PROCEDURE userinsert (username IN VARCHAR2, password IN VARCHAR2, useridReturn out number)
IS
BEGIN
    useridReturn := generateid.nextval;
    INSERT INTO users VALUES (useridReturn, username, password);
    commit;
END;