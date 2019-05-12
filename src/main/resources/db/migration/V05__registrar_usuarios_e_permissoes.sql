insert into permissao (descricao) values ('ROLE_ATUALIZAR_CATEGORIA');
insert into permissao (descricao) values ('ROLE_REMOVER_CATEGORIA');

insert into permissao (descricao) values ('ROLE_ATUALIZAR_PESSOA');

insert into permissao (descricao) values ('ROLE_ATUALIZAR_LANCAMENTO');

--admin
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,9);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,10);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,11);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,12);