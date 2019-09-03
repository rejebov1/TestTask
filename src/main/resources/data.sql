create table if not exists hiendsys."user"
(
  id bigserial not null
    constraint user_pk
      primary key,
  username varchar(50),
  password varchar(300),
  first_name varchar(50),
  last_name varchar(50),
  role varchar(10),
  status boolean,
  created_at timestamp
);

alter table hiendsys."user" owner to postgres;

create unique index if not exists user_username_uindex
  on hiendsys."user" (username);

