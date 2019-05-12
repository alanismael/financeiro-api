package com.example.financeiro.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.financeiro.api.modelo.Lancamento;
import com.example.financeiro.api.repositorio.lancamento.LancamentoRepositorioQuery;

@Repository
public interface LancamentoRepositorio extends JpaRepository<Lancamento, Long>, LancamentoRepositorioQuery {
	Lancamento findByDescricao(String descricao);
}
