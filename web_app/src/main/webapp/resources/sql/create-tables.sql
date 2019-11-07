drop table if exists greeting;
create table greeting
     (
     	id integer not null auto_increment
     		constraint greeting_pk
     			primary key,
     	content varchar(500) not null,
     	nickname varchar(500) not null,
     	datetime timestamp not null,
     	room integer not null,
     );