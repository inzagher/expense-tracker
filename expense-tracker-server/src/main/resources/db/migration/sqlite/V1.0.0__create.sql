create table backups (id integer not null, categories integer, expenses integer, persons integer, time datetime, primary key (id));
create table persons (id GUID not null, name varchar(255), primary key (id));
create table categories (id GUID not null, name varchar(255), color_red integer, color_green integer, color_blue integer, description varchar(255), obsolete boolean, primary key (id));
create table expenses (id GUID not null, date datetime, amount numeric(19,2), category_id GUID, person_id GUID, description varchar(255), primary key (id));