create or replace PROCEDURE removeAccount (bankAccountId IN number)  
IS
BEGIN
    DELETE FROM bankaccount WHERE accountid = bankAccountId;
    commit;
END removeAccount;