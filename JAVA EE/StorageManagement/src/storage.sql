/*create database storage;*/
/*use storage;
create table tb_user(id int primary key auto_increment, name varchar(20), permission int);
create table tb_storage(id int primary key auto_increment, address varchar(20));
create table tb_good(id int primary key auto_increment, name varchar(20), size int, color varchar(20), num int, storageid int , passman varchar(20), foreign key(storageid) references tb_storage(id));

insert into tb_good(name, size, color, num, store_id, passman) values('', 123, '', 234, 1, '');
insert into tb_storage(address) values('');*/

use storage;
/*drop table tb_user;
create table tb_user(id int primary key auto_increment, name varchar(20), permission int, password varchar(20));*/

/*update tb_good set name='cabbage', size=13, color='red', num=12, storageid=3, passman='xiaoming' where id=3;*/
/*delete from tb_good where id=4;*/
