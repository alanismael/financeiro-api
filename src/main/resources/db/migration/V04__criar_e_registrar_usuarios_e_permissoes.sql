CREATE TABLE IF NOT EXISTS usuario (
  codigo BIGINT(20) NOT NULL AUTO_INCREMENT,
  nome VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  senha VARCHAR(150) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS permissao (
  codigo BIGINT(20) NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS usuario_permissao (
  usuario_codigo BIGINT(20) NOT NULL,
  permissao_codigo BIGINT(20) NOT NULL,
  PRIMARY KEY (`usuario_codigo`, `permissao_codigo`),
  INDEX `fk_usuario_permissao_permissao1_idx` (`permissao_codigo` ASC),
  CONSTRAINT `fk_usuario_permissao_usuario1`
    FOREIGN KEY (`usuario_codigo`)
    REFERENCES usuario (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_permissao_permissao1`
    FOREIGN KEY (`permissao_codigo`)
    REFERENCES permissao (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

insert into usuario (nome, email, senha) values ('Administrador', 'admin@maissistemas.com', '$2a$10$/ZM9bSJwerEulFk/tKaoeeaCa.7OINIOu5KPeXpYD1GC74PDLpgTO');
insert into usuario (nome, email, senha) values ('Maria', 'maria@maissistemas.com', '$2a$10$EWCW.vL4z7dcrq6UdeYLY.SNXNH6vOoU1QaXHtEUtt8QlsNfUC9xy');

insert into permissao (descricao) values ('ROLE_CADASTRAR_CATEGORIA');
insert into permissao (descricao) values ('ROLE_PESQUISAR_CATEGORIA');

insert into permissao (descricao) values ('ROLE_CADASTRAR_PESSOA');
insert into permissao (descricao) values ('ROLE_REMOVER_PESSOA');
insert into permissao (descricao) values ('ROLE_PESQUISAR_PESSOA');

insert into permissao (descricao) values ('ROLE_CADASTRAR_LANCAMENTO');
insert into permissao (descricao) values ('ROLE_REMOVER_LANCAMENTO');
insert into permissao (descricao) values ('ROLE_PESQUISAR_LANCAMENTO');

--admin
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,1);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,2);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,3);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,4);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,5);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,6);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,7);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (1,8);

--maria
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (2,2);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (2,5);
insert into usuario_permissao (usuario_codigo, permissao_codigo) values (2,8);