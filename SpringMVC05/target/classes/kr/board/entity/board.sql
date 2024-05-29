drop table mem_auth; -- 참조테이블 먼저 지운다.
drop table mem_stbl;


create table mem_stbl(
	memIdx int not null, -- 자동증가X
	memID varchar(20) not null,
	memPassword varchar(200) not null,
	memName varchar(20) not null,
	memAge int,
	memGender varchar(20),
	memEmail varchar(50),
	memProfile varchar(50), 
	primary key(memID)
);


create table mem_auth(
no int not null auto_increment, 
memID varchar(50) not null, 
auth varchar(50) not null, 
primary key(no),
constraint fk_member_auth foreign key(memID) references mem_stbl(memID)
);

select * from mem_stbl;
select * from mem_auth;

     insert into
     mem_stbl(memIdx,memID,memPassword,memName,memAge,memGender,memEmail,memProfile)
     values((select IFNULL(MAX(memIdx)+1,1) from mem_stbl mem) ,
     'bitcocom2','1234','박매일2','24','남자','test@naver.com',null)