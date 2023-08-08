create table notes (
    id bigserial primary key,
    date timestamp without time zone,
    person_id integer,
    subject varchar(512),
    content varchar(4096),
    resolved boolean
);

alter table notes add constraint notes_person_id_fk foreign key (person_id) references persons(id);