create table users(
    id bigint not null auto_increment primary key,
    username varchar(100) UNIQUE not null,
    password varchar(255) not null
);