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
drop table if exists `exam_record`;

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


create table exam_category(
	id integer key auto_increment,
    `name` varchar(20),
    `description` text
);

create table exam_level(
	id integer key auto_increment,
    `name` varchar(20) not null unique,
    `description` text
);

create table exam(
	id integer key auto_increment,
    `name` varchar(50) unique not null,
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

create table exam_record(
	id integer key auto_increment,
    joiner_id integer, 
    exam_id integer,
    score integer
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

create table question(
	id integer key auto_increment,
    `name` text,
    level_id integer,
    category_id integer,
    creator_id integer,
    option_ids varchar(200),
    answer_ids varchar(200),
    type_id integer,
    `description` text,
	create_date date,
	update_date date
);


insert into exam_category(`name`) values('数学');
insert into exam_category(`name`) values('物理');
insert into exam_category(`name`) values('计算机');
insert into exam_category(`name`) values('化学');
insert into exam_category(`name`) values('web');

insert into question_category(`name`) values('数学');
insert into question_category(`name`) values('物理');
insert into question_category(`name`) values('计算机');
insert into question_category(`name`) values('web');

insert into exam_level(`name`) values('hard');
insert into exam_level(`name`) values('mid');
insert into exam_level(`name`) values('easy');

insert into question_level(`name`) values('hard');
insert into question_level(`name`) values('mid');
insert into question_level(`name`) values('easy');






INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Basic Math',1,2,100,111);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Trigonometry',2,2,100,73);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Leadership',2,2,120,78);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Physical Education',2,2,120,87);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Ecology',2,1,120,65);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Science',3,1,100,72);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Modern Literature',1,2,120,68);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Health',3,2,121,111);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Leadership1',2,2,122,66);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Leadership2',2,2,121,119);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('German',2,2,121,119);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Design and technology',1,1,122,97);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('LOGIC',3,1,211,71);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('LOGIC1',2,2,211,98);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Dramatics',2,2,121,80);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Spanish',3,1,121,118);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Instrumental Music',3,2,121,62);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Earth Science',1,1,121,73);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('Dramatics1',3,2,121,60);
INSERT INTO exam(`name`,category_id,level_id,score,time_limit) VALUES('German1',2,2,121,75);
    
insert into question_type(id,`type`) values(1,'单选题');
insert into question_type(id,`type`) values(2,'多选题');
insert into question_type(id,`type`) values(3,'判断题');



insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('完整的计算机系统应包括',1,3,1,'1-2-3-4','4');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('计算机硬件能直接识别的只有',1,3,1,'5-6-7-8','7');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('冯·诺依曼计算机中指令和数据均以二进制形式存放在存储器中， CPU 区分它们的依据是',1,3,1,'9-10-11-12','4');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('下列属于计算机硬件的是',1,3,2,'15-16-17-18','15-18');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('下列属于属于高级语言的是',1,3,2,'19-20-21-22','19-20');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('下列属于计算机外接设备的是',1,3,2,'23-24-25-26','23-24-25');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('同一个CPU 周期中，可以并行执行的微操作叫相容性操作',1,3,3,'13-14','14');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('就微命令的编码方式而言，若微操作命令的个数已确定，则直接表示法比编码表示法的微指令字长短 ',1,3,3,'13-14','13');
insert into question(`name`,level_id,category_id,type_id,option_ids,answer_ids) values('相对于微程序控制器，硬布线控制器的特点是指令执行速度慢，指令功能的修改和扩展容易',1,3,3,'13-14','14');

insert into question_option(content) values('运算器、存储器、控制器');
insert into question_option(content) values('外部设备和主机');
insert into question_option(content) values('主机和实用程序 ');
insert into question_option(content) values('配套的硬件设备和软件系统');
insert into question_option(content) values('高级语言');
insert into question_option(content) values('自然语言');
insert into question_option(content) values('汇编语言');
insert into question_option(content) values('机器语言');
insert into question_option(content) values('指令操作码的译码结果');
insert into question_option(content) values('指令和数据的寻址方式');
insert into question_option(content) values('指令周期的不同阶段');
insert into question_option(content) values('指令和数据所在的存储单元');
insert into question_option(content) values('是');
insert into question_option(content) values('否');

insert into question_option(content) values('CPU');-- 15
insert into question_option(content) values('腾讯QQ');
insert into question_option(content) values('微信');
insert into question_option(content) values('显卡');

insert into question_option(content) values('C++');-- 19
insert into question_option(content) values('java');
insert into question_option(content) values('汇编');
insert into question_option(content) values('机器语言');

insert into question_option(content) values('键盘');-- 23
insert into question_option(content) values('鼠标');
insert into question_option(content) values('音响');
insert into question_option(content) values('CPU');






INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,1,22);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,2,59);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,3,1);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,4,25);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,5,45);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,6,94);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,7,82);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,8,34);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,9,95);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,10,2);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,11,92);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,12,77);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,13,71);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,14,66);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,15,57);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,16,70);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,17,24);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,18,39);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,19,36);
INSERT INTO exam_record(joiner_id,exam_id,score) VALUES(1,20,49);


select * from question_option;

select * from question;
select * from exam;

select * from exam_record;


select e.id as `serial`,e.`name` as `name`,ec.`name` as `subject`,el.`name` as difficulty,e.score as score,e.time_limit as duration,er.score as getScore
	from exam_record er,exam e,exam_category ec,exam_level el 
    where er.joiner_id = 1 and e.id = er.exam_id and e.category_id = ec.id and e.level_id = el.id;
    

select * from `user`;



select q.id as `serial`,q.`name` as `name`,ql.`name` as difficulty,qc.`name` as `subject`,qt.`type` as `category` from question q,question_level ql,question_type qt,question_category qc where q.level_id = ql.id and q.category_id = qc.id and q.level_id = ql.id and q.type_id = qt.id;





 