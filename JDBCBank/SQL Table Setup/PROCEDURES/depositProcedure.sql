create or replace PROCEDURE DEPOSIT (bankAccountId IN number, amountToDeposit IN number)  
AS
oldamount number(10,2);
newamount number(10,2);
BEGIN
  SELECT funds INTO oldamount FROM bankaccount WHERE accountid = bankAccountId;
  newamount := oldamount + amountToDeposit;
  UPDATE bankaccount SET funds = newamount where accountid = bankAccountId;
  commit;
  
END DEPOSIT;