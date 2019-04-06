-- <Tables creating>

create table variants
(
  variant_id bigserial
    constraint variants_pk
      primary key,
  variant_number int not null,
  variant_rightequivalent varchar not null,
  variant_text varchar not null
);

create table words
(
  word_id bigserial
    constraint table_name_pk
      primary key,
  word_name varchar not null,
  word_description varchar,
  word_translation varchar,
  word_picture varchar
);

create table administrators
(
  user_id bigserial
    constraint administrators_pk
      primary key,
  user_login varchar not null,
  user_password varchar not null,
  user_email varchar not null,
  administrator_access int not null
);

create unique index administrators_user_login_uindex
  on administrators (user_login);

create table notifications
(
  notification_id bigserial,
  notification_generatedTimestamp TIMESTAMP not null,
  notification_isviewed BOOLEAN not null,
  notification_text varchar not null
);
-- </Tables creating>