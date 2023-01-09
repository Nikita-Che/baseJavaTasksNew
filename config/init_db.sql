create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text NOT NULL
);

alter table resume
    owner to postgres;

create table contact
(
    id          integer  not null
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

create table section
(
    id          integer  not null
        constraint section_pk
            primary key,
    type        text     not null,
    content       text     not null,
    resume_uuid char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (id, resume_uuid, type);

select * from resume