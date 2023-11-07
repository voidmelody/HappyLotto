create table user
(
    user_id    binary(16)    not null
        primary key,
    username   varchar(20)   not null,
    email      varchar(50)   not null,
    revenue    int default 0 null,
    created_at datetime(6)   not null,
    updated_at datetime(6)   null
);

create table orders
(
    order_id   binary(16)  not null
        primary key,
    user_id    binary(16)  not null,
    created_at datetime(6) not null,
    constraint fk_orders_to_user
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create table lotto
(
    lotto_id bigint auto_increment
        primary key,
    user_id  binary(16)    not null,
    order_id binary(16)    not null,
    number   varchar(100)  not null,
    prize    int default 0 null,
    constraint fk_lotto_to_orders
        foreign key (order_id) references orders (order_id),
    constraint fk_lotto_to_user
        foreign key (user_id) references user (user_id)
            on delete cascade
);

