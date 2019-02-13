CREATE TABLE USERS 
(
  USERID NUMBER(10, 0) NOT NULL 
, USERNAME VARCHAR2(255 BYTE) NOT NULL 
, PASSWORD VARCHAR2(255 BYTE) NOT NULL 
, CONSTRAINT SYS_C005180 PRIMARY KEY 
  (
    USERID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C005180 ON USERS (USERID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;

ALTER TABLE USERS
ADD CONSTRAINT USERS_UK1 UNIQUE 
(
  USERNAME 
)
USING INDEX 
(
    CREATE UNIQUE INDEX USERS_UK1 ON USERS (USERNAME ASC) 
    LOGGING 
    TABLESPACE USERS 
    PCTFREE 10 
    INITRANS 2 
    STORAGE 
    ( 
      INITIAL 65536 
      NEXT 1048576 
      MINEXTENTS 1 
      MAXEXTENTS UNLIMITED 
      BUFFER_POOL DEFAULT 
    ) 
    NOPARALLEL 
)
 ENABLE;



CREATE TABLE BANKACCOUNT 
(
  ACCOUNTID NUMBER(10, 0) NOT NULL 
, ACCOUNTNAME VARCHAR2(255 BYTE) NOT NULL 
, USERID NUMBER(10, 0) NOT NULL 
, FUNDS NUMBER(10, 2) DEFAULT 0 NOT NULL 
, CONSTRAINT SYS_C005184 PRIMARY KEY 
  (
    ACCOUNTID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX SYS_C005184 ON BANKACCOUNT (ACCOUNTID ASC) 
      LOGGING 
      TABLESPACE USERS 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE USERS 
PCTFREE 10 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;

ALTER TABLE BANKACCOUNT
ADD CONSTRAINT SYS_C005185 FOREIGN KEY
(
  USERID 
)
REFERENCES USERS
(
  USERID 
)
ENABLE;


CREATE SEQUENCE  "ROOT"."GENERATEID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 521 CACHE 20 NOORDER  NOCYCLE  NOPARTITION ;

create or replace PROCEDURE bankinsert (accountname IN VARCHAR2, userid IN int, bankaccountID OUT int)
IS
BEGIN
    bankaccountID := generateid.nextval;
    INSERT INTO bankaccount VALUES (bankaccountID, accountname, userid, 0);
    --commit;
END;

create or replace PROCEDURE DEPOSIT (bankAccountId IN number, amountToDeposit IN number)  
AS
oldamount number(10,2);
newamount number(10,2);
BEGIN
  SELECT funds INTO oldamount FROM bankaccount WHERE accountid = bankAccountId;
  newamount := oldamount + amountToDeposit;
  UPDATE bankaccount SET funds = newamount where accountid = bankAccountId;
  --commit;
  
END DEPOSIT;

create or replace PROCEDURE removeAccount (bankAccountId IN number)  
IS
BEGIN
    DELETE FROM bankaccount WHERE accountid = bankAccountId;
    --commit;
END removeAccount;


create or replace PROCEDURE REMOVEUSER 
(
  USERNAMEARG IN VARCHAR2,
  useridOut OUT number
) AS
usernamepairedid number;
BEGIN
  usernamepairedid := -1;
  SELECT userid into usernamepairedid from users where username = USERNAMEARG;
  
  
  
  DELETE FROM bankaccount where userid = usernamepairedid;
  
  DELETE FROM users where usernamepairedid = userid;
  
  useridOut := usernamepairedid;
  
  --commit;
  
END REMOVEUSER;

create or replace PROCEDURE UPDATEUSERNAME 
(
  OLDUSERNAME IN VARCHAR2 
, NEWUSERNAME IN VARCHAR2 
) AS 
BEGIN
  UPDATE users SET username = NEWUSERNAME WHERE username = OLDUSERNAME;
  
END UPDATEUSERNAME;

create or replace PROCEDURE UPDATEUSERPASSWORD 
(
  USERNAMEARG IN VARCHAR2 
, NEWPASSWORD IN VARCHAR2 
) AS 
BEGIN
  UPDATE users set password = newpassword where username = usernameArg;
  --commit;
END UPDATEUSERPASSWORD;


create or replace PROCEDURE userinsert (username IN VARCHAR2, password IN VARCHAR2, useridReturn out number)
IS
BEGIN
    useridReturn := generateid.nextval;
    INSERT INTO users VALUES (useridReturn, username, password);
    --commit;
END;


create or replace PROCEDURE WITHDRAW (bankaccountid IN number, amountToDeposit IN number)  
AS
oldamount number(10,2);
newamount number(10,2);
BEGIN
  SELECT funds INTO oldamount FROM bankaccount WHERE accountid = bankAccountId;
  newamount := oldamount - amountToDeposit;
  UPDATE bankaccount SET funds = newamount where accountid = bankAccountId;
  --commit;
END WITHDRAW;
