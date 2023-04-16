create table authorizations (
                        id BIGINT AUTO_INCREMENT NOT NULL,
                        currency VARCHAR(500) NOT NULL,
                        amount DECIMAL(17,2) NOT NULL
);

insert into authorizations (currency, amount) values ('USD', 10.12);
insert into authorizations (currency, amount) values ('JPY', 1234);
