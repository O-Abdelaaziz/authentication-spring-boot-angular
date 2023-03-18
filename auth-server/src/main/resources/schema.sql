create table if not exists users (
    id int not null AUTO_INCREMENT,
    first_name varchar (255) not null,
    last_name varchar (255) not null,
    email varchar (255) not null,
    password varchar (255) not null,
    PRIMARY KEY (id)
);

-- alter table if exists users add constraint uq_email unique (email);
ALTER TABLE users ADD CONSTRAINT IF NOT EXISTS email_unique UNIQUE (email);

create table if not exists tokens (
    id int not null AUTO_INCREMENT,
    refresh_token varchar (255) not null,
    expired_at datetime not null,
    issued_at datetime not null,
    user_id bigint (255) not null,
    PRIMARY KEY (id),
    constraint fk_token_user foreign key (user_id) references users (id)
);
