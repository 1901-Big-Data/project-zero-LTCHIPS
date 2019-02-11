create or replace PROCEDURE WITHDRAW (bankaccountid IN number, amountToDeposit IN number)  
AS
oldamount number(10,2);
newamount number(10,2);
BEGIN
  SELECT funds INTO oldamount FROM bankaccount WHERE accountid = bankAccountId;
  newamount := oldamount - amountToDeposit;
  UPDATE bankaccount SET funds = newamount where accountid = bankAccountId;
  commit;
END WITHDRAW;