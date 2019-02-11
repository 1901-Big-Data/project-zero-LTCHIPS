create or replace PROCEDURE REMOVEUSER 
(
  USERNAMEARG IN VARCHAR2 
) AS
usernamepairedid number;
BEGIN
  SELECT userid into usernamepairedid from users where username = USERNAMEARG;
  
  DELETE FROM bankaccount where userid = usernamepairedid;
  
  DELETE FROM users where usernamepairedid = userid;
  
  commit;
  
END REMOVEUSER;