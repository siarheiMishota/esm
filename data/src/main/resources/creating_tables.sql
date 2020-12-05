create table tag
(
    id   int auto_increment,
    name varchar(50) not null unique,

    primary key (id),

    index tag_name_index (name)
);

create table gift_certificate
(
    id               int auto_increment,
    name             varchar(70) not null,
    description      text        not null,
    price            decimal     not null check ( price >= 0 ),
    creation_date    timestamp   not null default now(),
    last_update_date timestamp   not null default now(),
    duration         int         not null check ( duration >= 0 ),

    primary key (id)
);

create table tag_gift_certificate
(
    tag_id              int not null,
    gift_certificate_id int not null,

    primary key (tag_id, gift_certificate_id),

    foreign key (tag_id) references tag (id),
    foreign key (gift_certificate_id) references gift_certificate (id)

);

create table user
(
    id       int auto_increment,
    name     varchar(50)  not null,
    email    varchar(50)  not null,
    password varchar(100) not null,
    role enum('ROLE_ADMIN','ROLE_USER') default 'ROLE_USER',

    primary key (id),

    unique (email),

    index user_name_email_index (email, name)
);

create table orders
(
    id                  int auto_increment,
    cost                decimal not null check ( cost > 0 ),
    date                timestamp default now(),
    id_gift_certificate int     not null,
    id_user             int     not null,

    primary key (id),

    foreign key (id_gift_certificate) REFERENCES gift_certificate (id),
    foreign key (id_user) REFERENCES user (id),

    index orders_cost_id_user_index (cost, id_user)
);