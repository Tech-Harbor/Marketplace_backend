create SCHEMA IF NOT EXISTS oranger;

create table if not exists advertisements (
    id                           bigserial primary key,
    active                       boolean        not null,
    auction                      boolean        not null,
    characteristic_advertisement text,
    create_date                  timestamp(6),
    update_active_date           timestamp(6),
    delivery                     varchar(255)   not null
    constraint advertisements_delivery_check check ((delivery)::text = ANY
        ((ARRAY ['NOVA_POSHTA'::character varying, 'MEEST_EXPRESS'::character varying,
          'JUSTIN'::character varying, 'UKRPOSHTA'::character varying, 'PICKUP'::character varying])::text[])),
    description_advertisement    text           not null,
    location                     varchar(255)   not null,
    name                         varchar(255)   not null,
    price                        numeric(38, 2) not null,
    category_id                  bigint         not null,
    user_id                      bigint
);

create table if not exists images (
    id               bigserial primary key,
    image_id         varchar(255),
    image_url        varchar(255),
    name             varchar(255),
    advertisement_id bigint constraint fkjibg9bsoybl2g8x1kdpgvgcvx references advertisements
);

create table if not exists categories (
    id       bigserial primary key,
    name     varchar(255) not null,
    image_id bigint
    constraint uk_6b3bn760mqxmhflt089q8ba00 unique constraint fkqhmw54g2p4xu0k71mblvlqfvi references images
);

create table if not exists users (
    id                  bigserial primary key,
    create_data         timestamp(6),
    email               varchar(255) not null constraint uk_6dotkott2kjsp8vw4d0m25fb7 unique,
    enabled             boolean,
    account_expired     boolean,
    account_locked      boolean,
    credentials_expired boolean,
    firstname           varchar(255) not null,
    lastname            varchar(255) not null,
    password            varchar(255) not null,
    phone               varchar(255) not null,
    register_status     varchar(255)
    constraint users_register_status_check check ((register_status)::text = ANY
        ((ARRAY ['GOOGLE'::character varying, 'JWT'::character varying])::text[])),
    roles               varchar(255)[],
    image_id            bigint
    constraint uk_94dj9ry3k3tmcsyg8eatp7vvn unique constraint fk17herqt2to4hyl5q5r5ogbxk9 references images
);

alter table advertisements
    add constraint fkj69sc4qf7g4p52q8vl0hqvbmp
        foreign key (user_id) references users;

alter table advertisements
    add constraint fkq2bx00e2ngwnvvuneonbnemvj
        foreign key (category_id) references categories;
