create database if not exists online_exam;
use online_exam;
drop table if exists `user`;
drop table if exists `exam`;
drop table if exists `exam_category`;
drop table if exists `exam_level`;
drop table if exists `question`;
drop table if exists `question_category`;
drop table if exists `question_level`;
drop table if exists `question_option`;

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

create table exam(
	id integer key auto_increment,
    `name` varchar(20),
    category_id integer,
    level_id integer,
    score integer,
    time_limit integer,
    `description` text,
    question_ids varchar(200),
    create_date date,
	update_date date
);

create table exam_category(
	id integer key auto_increment,
    `name` varchar(20) not null unique,
    `description` text
);

create table exam_level(
	id integer key auto_increment,
    `name` varchar(20) not null unique,
    `description` text
);

create table question(
	id integer key auto_increment,
    `name` varchar(20),
    score integer,
    level_id integer,
    category_id integer,
    creator_id integer,
    option_id integer,
    `description` text,
	create_date date,
	update_date date
);

create table question_category(
	id integer key auto_increment,
    `name` varchar(20),
    `description` text
);

create table question_level(
	id integer key auto_increment,
    `name` varchar(20),
    `description` text
);

create table question_option(
	id integer key auto_increment,
    content integer,
    `description` text
);

insert into exam_category(`name`) values('math');
insert into exam_category(`name`) values('physics');
insert into exam_category(`name`) values('computer');

insert into exam_level(`name`) values('hard');
insert into exam_level(`name`) values('mid');
insert into exam_level(`name`) values('easy');

insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('三角函数',1,1,100,90);
insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('线性代数',1,2,100,90);
insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('算法',3,2,100,120);






 