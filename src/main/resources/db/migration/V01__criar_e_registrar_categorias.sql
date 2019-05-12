create table categoria (
		codigo bigint(20) primary key auto_increment,
		nome varchar(50) not null
) ENGINE=InnoDB default charset=utf8;

insert into categoria (nome) values ('Lazer');
insert into categoria (nome) values ('Alimentação');
insert into categoria (nome) values ('Supermercado');
insert into categoria (nome) values ('Farmácia');
insert into categoria (nome) values ('Viagem');
insert into categoria (nome) values ('Mecânica');
insert into categoria (nome) values ('Combustível');
insert into categoria (nome) values ('Igreja');
insert into categoria (nome) values ('Imóvel');
insert into categoria (nome) values ('Trabalho');