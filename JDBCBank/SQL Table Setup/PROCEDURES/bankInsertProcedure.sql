create or replace PROCEDURE bankinsert (accountname IN VARCHAR2, userid IN int, bankaccountID OUT int)
IS
BEGIN
    INSERT INTO bankaccount VALUES (generateid.nextval, accountname, userid, 0);
    commit;
END;