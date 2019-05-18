CREATE TABLE IF NOT EXISTS pessoa (
  codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  ativo TINYINT(1) NOT NULL,
  logradouro VARCHAR(50) NULL,
  numero VARCHAR(5) NULL,
  complemento VARCHAR(5) NULL,
  bairro VARCHAR(50) NULL,
  cep VARCHAR(9) NULL,
  cidade VARCHAR(50) NULL,
  estado VARCHAR(2) NULL)
ENGINE = InnoDB default CHARSET = utf8;

insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Alan Ismael', true, 'Al. Pastos Bons', '31', 'A', 'Jardim Tropical', '65110-000', 'São José de Ribamar', 'MA');
insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Raymara Luz', false, 'Travessa da União', '10', '', 'Santa Efigênia', '65158-000', 'São Luís', 'MA');
insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Antônia Pereira', false, 'Avenida Principal', '200', '', 'Bairro Novo', '35800-000', 'Pinheiro', 'MA');
insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Richard Morales', false, 'Rua da Felicidade', '513', '', 'Cohama', '65200-000', 'Rio de Janeiro', 'RJ');
insert into pessoa (nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado) values ('Cesare Battisti', false, 'Rua Grande', '15', '', 'Centro', '78000-000', 'São Paulo', 'SP');