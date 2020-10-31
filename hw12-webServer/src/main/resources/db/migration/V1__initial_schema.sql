create table users (id bigint not null auto_increment , name varchar(255), age int, login varchar(255), password varchar(255), user_id bigint, primary key (id));
create table address (address_id bigint not null, street varchar(255), primary key (address_id));
create table phone (phone_id bigint not null, number varchar(255), user_id bigint,primary key (phone_id));
create sequence hibernate_sequence start with 1 increment by 1;