create table doctors(
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    specialty varchar(100) not null,
    street VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255),
    cep VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    number VARCHAR(10),
    complement VARCHAR(255)
);