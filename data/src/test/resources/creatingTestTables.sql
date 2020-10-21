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
    price            decimal     not null,
    creation_date    timestamp   not null,
    last_update_date timestamp   not null,
    duration         int         not null,

    primary key (id)
);

create table tags_gift_certificates
(
    tag_id              int not null,
    gift_certificate_id int not null,

    primary key (tag_id,gift_certificate_id),

    foreign key (tag_id) references tags (id) on delete cascade,
    foreign key (gift_certificate_id) references gift_certificates (id) on delete cascade

);

insert into tags(name)
values
       ('extreme'),
       ('fun'),
       ('love'),
       ('relax')
       ;

insert into gift_certificates (name, description, price, creation_date, last_update_date, duration)
values
('name 1', 'description 1',1,now(),now(),1),
('name 2', 'description 2',2,now(),now(),2),
('name 3', 'description 3',3,now(),now(),3),
('name 4', 'description 4',4,now(),now(),4),
('name 5', 'description 5',5,now(),now(),5),
('name 6', 'description 6',6,now(),now(),6),
('name 7', 'description 7',7,now(),now(),7),
('name 8', 'description 8',8,now(),now(),8),
('name 9', 'description 9',9,now(),now(),9),
('name 10', 'description 10',10,now(),now(),10)
;

insert into tags_gift_certificates (tag_id, gift_certificate_id)
values
(1,1),
(2,2),
(3,3),
(1,4),
(2,5),
(3,6),
(1,7),
(2,8),
(3,9),
(3,10)
;