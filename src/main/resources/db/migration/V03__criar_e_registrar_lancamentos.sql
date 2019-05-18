CREATE TABLE IF NOT EXISTS lancamento (
  codigo BIGINT(20) NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(50) NOT NULL,
  data_vencimento DATE NOT NULL,
  data_pagamento DATE NULL,
  valor DECIMAL(10,2) NOT NULL,
  observacao VARCHAR(100) NULL,
  tipo VARCHAR(20) NOT NULL,
  pessoa BIGINT(20) NOT NULL,
  categoria BIGINT(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_lancamento_pessoa_idx` (`pessoa` ASC),
  INDEX `fk_lancamento_categoria1_idx` (`categoria` ASC),
  CONSTRAINT `fk_lancamento_pessoa`
    FOREIGN KEY (`pessoa`)
    REFERENCES pessoa (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lancamento_categoria1`
    FOREIGN KEY (`categoria`)
    REFERENCES categoria (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECEITA', 1, 1);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'DESPESA', 2, 2);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Top Club', '2017-06-10', null, 120, null, 'RECEITA', 3, 3);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECEITA', 3, 4);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('DMAE', '2017-06-10', null, 200.30, null, 'DESPESA', 3, 5);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECEITA', 4, 6);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Bahamas', '2017-06-10', null, 500.00, null, 'RECEITA', 1, 7);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'DESPESA', 4, 8);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'DESPESA', 3, 9);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECEITA', 5, 10);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Café', '2017-06-10', null, 8.32, null, 'DESPESA', 1, 5);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'DESPESA', 5, 4);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'DESPESA', 4, 3);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'DESPESA', 4, 2);
insert into lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, pessoa, categoria) values ('Lanche', '2017-06-10', null, 10.20, null, 'DESPESA', 4, 1);