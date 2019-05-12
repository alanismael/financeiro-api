package com.example.financeiro.api.repositorio.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.financeiro.api.modelo.Lancamento;
import com.example.financeiro.api.repositorio.filtro.LancamentoFiltro;
import com.example.financeiro.api.repositorio.projecao.LancamentoResumo;

public interface LancamentoRepositorioQuery {
	List<Lancamento> filtrar(LancamentoFiltro filtro);
	
	Page<Lancamento> filtrar(LancamentoFiltro filtro, Pageable pageable);
	
	Page<LancamentoResumo> resumir(LancamentoFiltro filtro, Pageable pageable);
}
