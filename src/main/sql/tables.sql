create table variants
(
  variant_id bigserial
    constraint variants_pk
      primary key,
  variant_number int not null,
  variant_rightequivalent varchar not null,
  variant_text varchar not null
);