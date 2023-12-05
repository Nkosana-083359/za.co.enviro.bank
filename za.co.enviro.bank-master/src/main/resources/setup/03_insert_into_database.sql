insert into bank_accounts (acc_balance,customer_id, account_type_id)
values (1229800,'1ff8e5ca-adfd-4513-a25c-74de71d242ee',1);


insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Savings',1000,0);

insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Current',0,100000);

insert into customer (identity_number,name,surname,phone_number)
values (1234567634523,'Zodwa','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (0107026171085,'Nxumelo','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (9907176012083,'Lucky','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (0406101139086,'John','Mkhize','0813574240');