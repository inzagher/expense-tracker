create table backups (id GUID not null, categories integer, expenses integer, persons integer, time datetime, primary key (id));
create table persons (id GUID not null, name varchar(255), primary key (id));
create table categories (id GUID not null, color_blue integer, color_green integer, color_red integer, description varchar(255), name varchar(255), primary key (id));
create table expense (id GUID not null, amount numeric(19,2), date datetime, description varchar(255), category_id GUID, person_id GUID, primary key (id));