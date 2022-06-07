create database if not exists online_exam;
use online_exam;

create table user(
	user_id integer key auto_increment,
    user_username text,
    user_nickname text,
    user_password text,
    user_role_id integer,
    user_avatar text,
    user_description text,
    user_email text,
    user_phone text
);

insert into user(user_username,user_password) values('cym','123');
select * from user;
 