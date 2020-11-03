create table backups (id blob not null, categories integer, expenses integer, persons integer, time datetime, primary key (id));
create table persons (id blob not null, name varchar(255), primary key (id));
create table categories (id blob not null, color_blue tinyint, color_green tinyint, color_red tinyint, description varchar(255), name varchar(255), primary key (id));
create table expense (id blob not null, amount float(19), date datetime, description varchar(255), category_id blob, person_id blob, primary key (id));