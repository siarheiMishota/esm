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
    creation_date    timestamp   not null,
    last_update_date timestamp   not null,
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
