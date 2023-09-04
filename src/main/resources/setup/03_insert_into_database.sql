insert into bank_accounts (acc_balance, account_type_id)
values (1000,8);


insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Savings',1000,0);

insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Current',0,100000);

insert into customer (identity_number,cname,surname,phone_number)
values (12345676,'Nkosana','Mdlalose','0813574240');