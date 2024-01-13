insert into bank_accounts (acc_balance,customer_id, account_type_id)
values (1229800,5,1);


insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Savings',1000,0);

insert into account_type_lookup ( account_type, minimum_balance, overdraft_limit)
values ('Current',0,100000);

insert into customer (identity_number,name,surname,phone_number)
values (1234567634523,'Zodwa','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (1234567634923,'Nxumelo','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (1234067639523,'Lucky','Mkhize','0813574240');

insert into customer (identity_number,name,surname,phone_number)
values (1234467634593,'John','Mkhize','0813574240');