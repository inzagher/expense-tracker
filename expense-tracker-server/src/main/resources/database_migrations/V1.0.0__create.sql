create table backups (
    id bigserial primary key,
    file_name varchar(32),
    created timestamp,
    categories integer,
    expenses integer,
    persons integer
);

create table persons (
    id bigserial primary key,
    name varchar(255)
);

create table categories (
    id bigserial primary key,
    name varchar(255),
    color_red integer,
    color_green integer,
    color_blue integer,
    description varchar(255),
    obsolete boolean
);

create table expenses (
    id bigserial primary key,
    date timestamp,
    amount numeric(19,2),
    category_id integer,
    person_id integer,
    description varchar(255)
);

alter table expenses add constraint expenses_person_id_fk foreign key (person_id) references persons(id);
alter table expenses add constraint expenses_category_id_fk foreign key (category_id) references categories(id);
create index expenses_date_idx on expenses(date);
create index expenses_description_idx on expenses(description);