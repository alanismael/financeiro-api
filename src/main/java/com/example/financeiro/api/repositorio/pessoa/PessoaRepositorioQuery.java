package com.example.financeiro.api.repositorio.pessoa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.financeiro.api.modelo.Pessoa;
import com.example.financeiro.api.repositorio.filtro.PessoaFiltro;

public interface PessoaRepositorioQuery {
	List<Pessoa> filtrar(PessoaFiltro filtro);
	
	Page<Pessoa> filtrar(PessoaFiltro filtro, Pageable pageable);
}
