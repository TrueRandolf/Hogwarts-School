alter table student add constraint age check (age>16);

alter table student alter column name  set not null;

alter table  student add constraint name unique (name);

alter table faculty  add constraint color_name unique (color,name);

alter table student alter column age set default 20;