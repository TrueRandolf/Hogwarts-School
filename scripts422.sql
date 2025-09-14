create table car (
id_car serial primary key,
brand varchar(255),
model varchar(255),
price int8
);

create table users (
id_user serial primary key,
name varchar(255),
age int4,
license boolean,
id_car integer
);

alter table users
add constraint id_car
foreign key (id_car) references car(id_car);