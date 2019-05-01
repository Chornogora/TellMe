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

create table tasks
(
  task_id bigserial
    constraint tasks_pk
      primary key,
  task_number int
);

create table grtheory
(
  task_id bigserial
    constraint grtheory_pk
      primary key
    constraint grtheory_tasks_task_id_fk
      references tasks,
  grtheory_content varchar not null
);

create table ltheory
(
  task_id bigserial
    constraint ltheory_pk
      primary key
    constraint ltheory_tasks_task_id_fk
      references tasks
);

alter table words
  add task_id bigint;

alter table words
  add constraint words_ltheory_task_id_fk
    foreign key (task_id) references ltheory;

create table tests
(
  task_id bigserial
    constraint tests_pk
      primary key,
  test_text varchar,
  test_picture varchar
);

alter table tests
  add constraint tests_tasks_task_id_fk
    foreign key (task_id) references tasks;

alter table variants
  add task_id bigint;

alter table variants
  add constraint variants_tests_task_id_fk
    foreign key (task_id) references tests;

create table lessons
(
  lesson_id bigserial
    constraint lessons_pk
      primary key,
  lesson_points int default 0 not null,
  lesson_level varchar not null,
  lesson_name varchar not null
);

create unique index lessons_lesson_name_uindex
  on lessons (lesson_name);

alter table tasks
  add lesson_id bigint;

alter table tasks
  add constraint tasks_lessons_lesson_id_fk
    foreign key (lesson_id) references lessons;

create table users
(
  user_id bigserial
    constraint users_pk
      primary key,
  user_login varchar not null,
  user_password varchar not null,
  user_email varchar not null,
  user_avatar varchar,
  user_birthday Date,
  user_signupdate Date not null,
  user_points int default 0 not null
);

create unique index users_user_login_uindex
  on users (user_login);

create table progresses
(
  progress_id bigserial
    constraint progresses_pk
      primary key,
  lesson_id bigserial not null
    constraint progresses_lessons_lesson_id_fk
      references lessons,
  user_id bigserial not null
    constraint progresses_users_user_id_fk
      references users,
  progress_taskpassednumber int default 0 not null,
  progress_isdone boolean default false not null
);

create table chats
(
  chat_id bigserial
    constraint chats_pk
      primary key,
  creator_id bigserial not null
    constraint chats_users_user_id_fk
      references users,
  chat_theme varchar not null
);

create table messages
(
  message_id bigserial
    constraint messages_pk
      primary key,
  chat_id bigserial not null
    constraint messages_chats_chat_id_fk
      references chats,
  sender_id bigserial not null
    constraint messages_users_user_id_fk
      references users,
  message_senttimestamp TIMESTAMP not null,
  message_text varchar not null
);

alter table notifications
  add user_id bigserial not null;

alter table notifications
  add constraint notifications_users_user_id_fk
    foreign key (user_id) references users;

alter table lessons
  add lesson_opened boolean default false not null;
-- </Tables creating>