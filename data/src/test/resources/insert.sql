create table gift_certificate
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

insert into gift_certificate (name, description,creation_date,last_update_date, price, duration) values('name 1', 'description 1',now(),now(), 1, 1);


