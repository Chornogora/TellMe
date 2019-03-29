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

-- </Tables creating>