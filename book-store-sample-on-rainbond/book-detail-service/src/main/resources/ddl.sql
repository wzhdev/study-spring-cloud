create table book_detail (
    id integer not null auto_increment,
    author varchar(32),
    isbn varchar(13),
    pages integer not null,
    pubdate timestamp,
    title varchar(126),
    primary key (id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
alter table book_detail add constraint book_detail_isbn unique (isbn);