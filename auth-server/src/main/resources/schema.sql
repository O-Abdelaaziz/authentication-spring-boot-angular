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
