create table backups (
    id bigserial primary key,
    created timestamp,
    categories integer,
    expenses integer,
    persons integer
);
alter table backups owner to sa;

create table persons (
    id bigserial primary key,
    name varchar(255)
);
alter table persons owner to sa;

create table categories (
    id bigserial primary key,
    name varchar(255),
    color_red integer,
    color_green integer,
    color_blue integer,
    description varchar(255),
    obsolete boolean
);
alter table categories owner to sa;

create table expenses (
    id bigserial primary key,
    date timestamp,
    amount numeric(19,2),
    category_id integer,
    person_id integer,
    description varchar(255)
);
alter table expenses owner to sa;
alter table expenses add constraint expenses_person_id_fk foreign key (person_id) references persons(id);
alter table expenses add constraint expenses_category_id_fk foreign key (category_id) references categories(id);
create index expenses_date_idx on expenses(date);
create index expenses_description_idx on expenses(description);