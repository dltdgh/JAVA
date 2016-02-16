/*
create database pojian;

use pojian;



drop table tb_info;
drop table tb_type;

create table tb_user(id int primary key auto_increment, user_name varchar(20) not null, user_password varchar(30) not null);

create table tb_type(id int(5), type_sign int(10) primary key, type_name varchar(20) not null, type_intro varchar(100));

create table tb_info(id int primary key, info_type int not null, info_title varchar(80) not null, info_linkman varchar(50) not null, info_phone int, info_date date, info_state varchar(10), info_content varchar(1000), info_email varchar(30), info_payfor varchar(20), foreign key(info_type) references tb_type(type_sign));

insert into tb_user values(null, 'dlt', '2345');

INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (1,1, 'recruit', '招聘信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (2,2, 'training', '培训信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (3, 3, 'house', '房屋信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (4,4, 'buy', '求购信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (5,5, 'invite', '招商引资');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (6, 6, 'apartment', '公寓信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (7,7, 'apply', '求职信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (8, 8, 'tutor', '家教信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (9,9, 'car', '车辆信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (10,10, 'sale', '出售信息');
INSERT INTO tb_type (id, type_sign, type_name, type_intro) VALUES (11,11, 'search', '寻找启示');

delete from tb_user;
INSERT INTO tb_user(id,user_name,user_password) VALUES (3,'a','a123');
INSERT INTO tb_user(id,user_name,user_password) VALUES (4,'b','a123');
*/
use pojian;

/*
drop table tb_info;

create table tb_info(id int primary key auto_increment, info_type int not null, info_title varchar(80) not null, info_linkman varchar(50) not null, info_phone varchar(20), info_date datetime, info_state varchar(10), info_content varchar(1000), info_email varchar(30), info_payfor varchar(20), foreign key(info_type) references tb_type(type_sign));
*/
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 1, '招聘信息标题', '招聘信息内容', '明明', '13222228888', 'mm@qq.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 2, '培训信息标题', '增训信息内容', '明明', '13222228888', 'mm@qq*.com', now(), '0', '0');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 3, '房屋信息标题', '房屋信息内容', '明明', '13222228888', 'mm@qq.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 4, '求购信息标题', '求购信息内容', '芳芳', '13222226666', 'ff@163.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 5, '招商引资标题', '招商引资内容', '芳芳', '13222226666', 'ff@163.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 6, '公寓信息标题', '公寓信息内容', '芳芳', '13222226666', 'ff@163.com', now(), '0', '0');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 7, '求职信息标题', '求职信息内容', '芳芳', '13222226666', 'ff@163.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 8, '家教信息标题', '家教信息内容', '小免', '13255557777', 'xt@163.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 9, '车辆信息标题', '车辆信息内容', '小免', '13255557777', 'xt@163.com', now(), '1', '0');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 10, '出售信息标题', '出售信息内容', '小免', '13255557777', 'xt@163.com', now(), '1', '1');
INSERT INTO tb_info (id,info_type,info_title,info_content,info_linkman,info_phone,info_email,info_date,info_state,info_payfor) VALUES (null, 11, '寻找启示标题', '寻找启示内容', '小免', '13255557777', 'xt@163.com', now(), '1', '1');
