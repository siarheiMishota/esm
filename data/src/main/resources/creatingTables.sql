create table tags
(
    id   int auto_increment,
    name varchar(30) not null unique,

    primary key (id)

);

create table gift_certificates
(
    id               int auto_increment,
    name             varchar(50) not null,
    description      text        not null,
    price            decimal     not null check ( price >= 0 ),
    creation_date    timestamp   not null default now(),
    last_update_date timestamp   not null default now(),
    duration         int         not null check ( duration >= 0 ),

    primary key (id)
);

create table tags_gift_certificates
(
    tag_id              int not null,
    gift_certificate_id int not null,

    primary key (tag_id, gift_certificate_id),

    foreign key (tag_id) references tags (id) on delete cascade,
    foreign key (gift_certificate_id) references gift_certificates (id) on delete cascade

);

create table users
(
    id       int auto_increment,
    name     varchar(50)  not null,
    email    varchar(50)  not null,
    password varchar(100) not null,

    primary key (id),

    unique (email)
);

create table orders
(
    id   int auto_increment,
    cost decimal not null check ( cost > 0 ),
    date timestamp default now(),
    id_gift_certificate int not null ,
    id_user int not null ,

    primary key (id),

    foreign key (id_gift_certificate) REFERENCES gift_certificates(id),
    foreign key (id_user) REFERENCES users(id)
);
