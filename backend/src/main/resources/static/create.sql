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
drop table if exists `question_type`;

create table user(
	user_id integer key auto_increment,
    user_username text,
    user_nickname text,
    user_password text,
    user_role_id integer,
    user_avatar text,
    user_description text,
    user_email text,
    user_phone text,
    user_exam_ids varchar(50)
);

create table exam(
	id integer key auto_increment,
    `name` varchar(20) unique not null,
    category_id integer,
    level_id integer,
    score integer,
    time_limit integer,
    `description` text,
    radio_ids varchar(200),
    check_ids varchar(200),
    judge_ids varchar(200),
    radio_points integer,
    check_points integer,
    judge_points integer,
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
    level_id integer,
    category_id integer,
    creator_id integer,
    option_ids varchar(200),
    type_id integer,
    
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
    content text,
    `description` text
);

create table question_type(
	id integer key auto_increment,
    `type` varchar(20)
);

insert into exam_category(`name`) values('数学');
insert into exam_category(`name`) values('物理');
insert into exam_category(`name`) values('计算机');
insert into exam_category(`name`) values('化学');


insert into exam_level(`name`) values('hard');
insert into exam_level(`name`) values('mid');
insert into exam_level(`name`) values('easy');

insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('三角函数',1,1,100,90);
insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('线性代数',1,2,100,90);
insert into exam(`name`,category_id,level_id,score,time_limit) 
	values('算法',3,2,100,120);
    
insert into question_type(id,`type`) values(1,'单选题');
insert into question_type(id,`type`) values(2,'多选题');
insert into question_type(id,`type`) values(3,'判断题');

insert into question(`name`,type_id,option_ids) values('单选1',1,'1-2-3');
insert into question(`name`,type_id,option_ids) values('单选2',1,'1-2-3');
insert into question(`name`,type_id,option_ids) values('单选3',1,'1-2-3');
insert into question(`name`,type_id,option_ids) values('多选1',2,'2-3');
insert into question(`name`,type_id,option_ids) values('多选2',2,'1-2-3');
insert into question(`name`,type_id,option_ids) values('多选3',2,'1-2-3');
insert into question(`name`,type_id,option_ids) values('判断1',3,'2-3');
insert into question(`name`,type_id,option_ids) values('判断2',3,'1-2');
insert into question(`name`,type_id,option_ids) values('判断3',3,'1-3');

insert into question_option(content) values('选项1');
insert into question_option(content) values('选项2');
insert into question_option(content) values('选项3');


select * from question_option;

select * from question;
select * from exam;
select * from `user`;







 