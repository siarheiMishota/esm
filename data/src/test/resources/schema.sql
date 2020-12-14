create table tag
(
    id   int auto_increment,
    name varchar(50) not null unique,

    primary key (id)

);

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

create table tag_gift_certificate
(
    tag_id              int not null,
    gift_certificate_id int not null,

    primary key (tag_id, gift_certificate_id),

    foreign key (tag_id) references tag (id) on delete cascade,
    foreign key (gift_certificate_id) references gift_certificate (id) on delete cascade

);

create table user
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
    id                  int auto_increment,
    cost                decimal not null check ( cost > 0 ),
    date                timestamp default now(),
    id_gift_certificate int     not null,
    id_user             int     not null,

    primary key (id),

    foreign key (id_gift_certificate) REFERENCES gift_certificate (id),
    foreign key (id_user) REFERENCES user (id)
);

insert into tag(name)
values ('extreme'),
       ('fun'),
       ('love'),
       ('relax'),
       ('bored');


insert into gift_certificate (name, description, price, creation_date, last_update_date, duration)
values ('name 1', 'description 1', 1, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 1),
       ('name 2', 'description 2', 2, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 2),
       ('name 3', 'description 3', 3, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 3),
       ('name 4', 'description 4', 4, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 4),
       ('name 5', 'description 5', 5, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 5),
       ('name 6', 'description 6', 6, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 6),
       ('name 7', 'description 7', 7, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 7),
       ('name 8', 'description 8', 8, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 8),
       ('name 9', 'description 9', 9, '2020-10-22T00:03:22.917992000', '2020-10-22T00:03:22.917992000', 9),
       ('name 10', 'description 10', 10, '2020-10-22T00:03:22.917992000', '2020-12-22T00:03:22.917992000', 10),
       ('name 11', 'description 11', 11, '2020-10-22T00:03:22.917992000', '2020-12-22T00:03:22.917992000', 11);

insert into tag_gift_certificate (tag_id, gift_certificate_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (1, 4),
       (2, 5),
       (3, 6),
       (1, 7),
       (2, 8),
       (3, 9),
       (3, 10),
       (2, 3);

insert into user (name, email, password)
values ('Jobi', 'jchessun0@moonfruit.com', 'afb591939cccb575099d348c23d7e5892a3c35c1a2bf5420d3b131c62cbd4ef7'),
       ('Nat', 'nlafont1@imgur.com', '6855d35eb88cfa08539aade1f8fc9d8f9ae82bf179d55a8a23c30412b4c83908'),
       ('Augy', 'aswigg2@kickstarter.com', 'f40573804c5e76900099fec371bff046b2b966eb178f1855a4941bf710845483'),
       ('Dena', 'dhamper3@rambler.ru', '1f74aff20b83704780f2de6ef5259739112f3ba27ced0718a7fbaa080ced92f6'),
       ('Jennee', 'jmottram1d@un.org', '7faab5f29ab9712326938b3267de8f5c764d3e097cf84d937ace6b29bed0016f'),
       ('Sarah', 'sprahm4@quantcast.com', '62b3dd516303461e96b90f868228cc5a4df3f0e445a6f7d0d6b41066c67a2652'),
       ('Osbert', 'olangeley5@mayoclinic.com', '4b63634ff91590cceb1d027e791441d5a5aa9d2e8f9d5c96cfb7d0893ae66125'),
       ('Keary', 'klorenzini6@yellowpages.com', '6d220d435b5fcf053695ca534dce6d399efadc101b3beb91282e27d06af802e0'),
       ('Petronilla', 'pseago7@techcrunch.com', 'a59bcb2c7247b2f89d9dd684c6aacf52644c8be26a11d550020228f3fe99df08'),
       ('Pincas', 'ptoomer8@blogtalkradio.com', '696ffc855badd67ac1b4e2d526b89bec155cdb5d3e44b3740bf9624665d5bf0e'),
       ('Ailina', 'arains9@mapquest.com', '1f705e1cd6ab072cb01b39ff271c70a589e49bed5f1e9743d5684b72f87c40c0'),
       ('Ofilia', 'ogatea@usa.gov', 'da23a22cab62c492ee6577573cc27a6841ce3c809bf906f0c986a90044ace133'),
       ('Aurthur', 'ayitzhokb@fc2.com', '942629d819df09230ffc7b03f83001840153dcf3f3320e5786ef0d7bc0bdf380'),
       ('Bryce', 'bcrowthec@nba.com', '635788165ba9b52a651400b5f62dab463bff4f210a3e9979e09df5accf43cfea'),
       ('Rennie', 'rrosind@histats.com', '54134a2e984df6608bc0669ddcf64ccf77aa435505cd0dff495194baf7b1ecc0'),
       ('Gennifer', 'gdowe@discuz.net', '9d7eee83afc7b60ca212e22e8339cf057c8931d66691e9009248eb0b6cc7d41c'),
       ('Ferguson', 'fcarstairsf@jigsy.com', 'e165d87c63d2eb946543c11e5a145ee1cd87a052762d8a43b7b88154d87c572b'),
       ('Juliann', 'jbalching@bravesites.com', 'f92b93ef3efca48a8c4515d1a39827f9795a9d4eba159f9cead11c953e997d40'),
       ('Bobbee', 'bpeterah@altervista.org', '969d3a1290e1cb9f572d2a28759eb80cb2880bc43b9040adf09df7ac3d159ad9'),
       ('Ola', 'omagoverni@pbs.org', '9fe0d8a2541feaa14ec48b670da2ebc53d496f6ac9bf0f266293dd72c45a9b7f'),
       ('Cristy', 'cwelfairj@icq.com', '947d0be7d635726550068e6cd57aec1b9c379ef00c63df9bd6405042de84244c'),
       ('Conant', 'cgunthorpek@sciencedaily.com', '2c28bd344b3871a05386cb5b1de959cc4f02d42291b26e9f8e0f72fd89acac07'),
       ('Marnia', 'mstlegerl@comcast.net', '72c44c07bd9faca91f5083dccfdbb2e7e6807a28dc2c1946a3e8c639a997adca'),
       ('Barbaraanne', 'bdobelm@exblog.jp', '5697ea184923c59a9d94f31e37c43e4f33e051f0e367185aec90697b58f1b0e5'),
       ('Chrysler', 'cgarfootn@census.gov', '9298bb8d1440c6b7e21c2fafaa5ba5b2999c411a924a3f062697b87d237ecf74'),
       ('Carney', 'cblaxillo@bbc.co.uk', '6161681185446641dbf3ef8297d392d1b47544d5f6aea205a11f8b0ee8427de0'),
       ('Munmro', 'marendtp@mlb.com', '4045e774cc10467db0a128e4fb34e212f6738b3af2e924b6fd7f0ab660b5f84a'),
       ('Reeva', 'rpenniallq@nbcnews.com', 'e0377d23e43c80c52d74516d4885bcfa775a3c7e07e29c70fd2e53d42df17da8'),
       ('Friedrich', 'fgarralsr@netlog.com', '6d5b4b32eb5e932aa511ba74a6fd56564cca313e23aec2ba1aab1dff93341091'),
       ('Lee', 'lloverings@fc2.com', '87aba6d97f6ea49c20c23ee09ed55b6d56a9ae91bf23b53c2ee9677d874de6b3'),
       ('Mylo', 'mdoyleyt@wix.com', '415610968a19a3b9c4300e1e9e45ca9560bb3e3348616251054ff3d996816be4'),
       ('Gal', 'gmacintyreu@xinhuanet.com', '412510fe0556f8d957dc468216b8bfced7c1de60ffc04b6116d70bedc5d21d1c'),
       ('Hercules', 'hdreierv@foxnews.com', '295df280b20f33ad493fe0674cf7dc2c94795d390bc787b33036808a31e3cf76'),
       ('Glynn', 'gerettw@desdev.cn', '17c181a260f5c5b5a7b4e9c755acdc1c1932275e7cba20d09c50a82cc397c2a6'),
       ('Betsey', 'bmctrustriex@fc2.com', '3c667dcb86b0eddc02ec08cdef343709059b4d2135b4639e307ecb4340255fbd'),
       ('Graehme', 'gcracoey@wordpress.com', 'a891e1210d120ce5fc2126385e428def5070fa7de377a951bde9a209be22c5ad'),
       ('Dev', 'dshanklez@google.com.br', 'daa139db1602e2cbffff27e3b802cee57281eed60a96a93a889cf611994e18fa'),
       ('Minor', 'mculligan10@disqus.com', 'f05c8b071fce52a9730843754214065a6846d5e10b65f5c0336fd4bb1ab05e15'),
       ('Clem', 'chandling11@mtv.com', '04846014a81571fd55b2cef6aad1bc73bda5a57e48e399b391dfd34ad9deed0a'),
       ('Otes', 'ogrelka12@hp.com', 'ca0bbd66bc6124d8ddca059bb0ad80013ae8a71f0f627aba8554dae7457ebec6'),
       ('Harlan', 'hgloster13@guardian.co.uk', 'ba1a1665aed9b39c2c07416c0ee4758ceed03bee1583bc1e8a16c05b7c494b24'),
       ('Crin', 'cjune14@whitehouse.gov', '014070723e9a0d941af6ee6f7d1b55d82dbd04ed2a625b1435af4a662c8d7c86'),
       ('Marylinda', 'mvella15@myspace.com', 'dd0a4f85018c6de9f857dcacbaf0256d7f67561be6f5965adec4a17a6e302398'),
       ('Arel', 'abrangan16@mysql.com', '013424589fa01432baf9884f719e149804dc5c4d6181a4a1c759ad676d0cd348'),
       ('Leeland', 'lswanborough17@si.edu', '67a9fb770d5974647e824230662fda982b0923b1aba33096b5b5173e5fc3e67e'),
       ('Leonard', 'lduffyn18@youku.com', '965b8b687ccd891361c72d8e6665f30a089313ee17cb9102234b1ec035ef92a5'),
       ('El', 'ehoyt19@posterous.com', '8b39f3467cdcb8a120257e09e09b5d58670754bf0350f5d9aa3acc01bebcb763'),
       ('Emmery', 'erigolle1a@tiny.cc', 'c4f11f789a3fea7a9fefba69ec8b5ba019a195db0bd1f6eae07602747a6cfc5b'),
       ('Dianne', 'dstennet1b@smugmug.com', '5a10885ce3a54bfd7aa0c0d948ff554db6869fc9e3bfb72f21637954fd593de5'),
       ('Ray', 'rglencros1c@blog.com', '89b21a8fe1e6336bd13ebc8dedd938d62a72b88ae78403d2d0354736889c3624'),
       ('Robinette', 'rpepper1e@virginia.edu', 'a5969daa67d9696744426590cd11f2a5b362f7d0c9d6205251f5226dfc6acabc'),
       ('Blondie', 'bvereker1f@kickstarter.com', 'ccf2668e92b0e405840bdf8a1e75897a10d7f9c8089c239763363bf0711ac091'),
       ('Kiel', 'kfolomkin1g@imageshack.us', '97d1b665b753e06b1901c71e3d1b9d1317a0bce6198342081200727a8e2f88c3'),
       ('Rich', 'rdeevey1h@ameblo.jp', '27703130b9eef590a9dac1e84c4a4cf5e5fd1d72a8e6f515d1be38c2d2f7f662'),
       ('Leshia', 'lfurminger1i@google.cn', 'bbf12726d10bcab6b5018c55909047ac0441f89822b16cd139d9b4e2b73cf8a7'),
       ('Kinsley', 'kconkling1j@linkedin.com', 'fa439fdd208d01b86b1674bc262aacddf948cf187ee1027c57378155c2a6d442'),
       ('Kipp', 'ksuttling1k@google.com.hk', '68951db8c8c6b1146bb72566ec91e1d3f3f8d2ab11daa21ae8a046378c919961'),
       ('Augusta', 'ayates1l@so-net.ne.jp', 'e26670414faad4fc8bdaba631f59c1ee53c7d7906a8e319dcaadc61691fcb771'),
       ('Hendrick', 'hwesson1m@gmpg.org', '428448728ba4a5df2b16fa41b32339302bf1f11d66fcc98fedcd051a70f47315'),
       ('Teodoor', 'thaley1n@photobucket.com', '1fde10d622c701f89deb27e814b74506f6128776dab024d90b0e08082498435e'),
       ('Berget', 'bchild1o@over-blog.com', '59992f0817e8f41d7bce2f5f73b4983ccf2f075234e4b9b51d3ee75e84baf5ec'),
       ('Mame', 'mgirardot1p@ovh.net', '49b7ec9c1afd8686345d397d92ecfbd1ca24ccd669503b9b62ec0b124a147141'),
       ('Orson', 'osouth1q@nhs.uk', '72d9908a2471b9093529cf1be64e88407db615c8bd0e59be5ac6c59a6048ecc7'),
       ('Noell', 'nrisson1r@bravesites.com', '221a046907c905fb207ab403f35065fb2c492c898fea23dcb07b4a3d204f06c0'),
       ('Gates', 'gbeazer1s@freewebs.com', 'd1bda7d9748b7613abe1a602cf78be26e963b3ff415cb459e8d41e2ffa1b34e8'),
       ('Nixie', 'ncody1t@blogtalkradio.com', 'b39f46d6876ba4088725f723f3d5f67ed36537e2b8ee11ea11c36ef85706cfd8'),
       ('Alejandra', 'aheller1u@instagram.com', '364930be9293270e16dd013e99ad1b0d9a91cf0e42f151ea9594127e39cbc3f4'),
       ('Theresa', 'twillis1v@dot.gov', '8c7e0879b46e008ba309aa5580b2bf5a08d0887d5cb8deb463df5c6e37d7e51c'),
       ('Fawn', 'ffiske1w@canalblog.com', '3964b95dcffea59dc7738cf2c0c7cba39c5ae0f794af6c83bd62515c496b0cf8'),
       ('Tiff', 'tpydcock1x@latimes.com', '79f8bc81a15481753c5f497e526ff532ba934c07fd8c8aced594a67614628e46'),
       ('Alonzo', 'asimm1y@blogs.com', '899693c6adfff111befafaa1a921564777016066b38fc88d4ea8e843627deda5'),
       ('Lane', 'ljohnsson1z@wired.com', '5414cd24d923e6b0034362095d326435dcf5a7ea8c224c053ac0b6022f1b8a14');

insert into orders (cost, id_gift_certificate, id_user)
values (1, 1, 60),
       (2, 2, 59),
       (3, 3, 58),
       (4, 4, 5),
       (6, 2, 5),
       (5, 5, 56),
       (6, 6, 55),
       (7, 7, 54),
       (8, 8, 53),
       (9, 9, 52),
       (10, 10, 51),
       (11, 11, 50);

