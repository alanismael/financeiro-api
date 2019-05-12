package com.example.financeiro.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.financeiro.api.modelo.Pessoa;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long> {
	Pessoa findByNome(String nome);
}
