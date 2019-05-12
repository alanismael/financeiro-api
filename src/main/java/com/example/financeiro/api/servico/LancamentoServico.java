package com.example.financeiro.api.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.financeiro.api.modelo.Lancamento;
import com.example.financeiro.api.modelo.Pessoa;
import com.example.financeiro.api.repositorio.LancamentoRepositorio;
import com.example.financeiro.api.repositorio.PessoaRepositorio;
import com.example.financeiro.api.repositorio.filtro.LancamentoFiltro;
import com.example.financeiro.api.repositorio.projecao.LancamentoResumo;
import com.example.financeiro.api.servico.excecao.PessoaInexistenteOuInativaException;

@Service
public class LancamentoServico {
	private final LancamentoRepositorio lancamentoRepositorio;
    
	@Autowired
    private PessoaServico pessoaServico;
    
    @Autowired
    public LancamentoServico(LancamentoRepositorio lancamentoRepositorio) {
        this.lancamentoRepositorio = lancamentoRepositorio;
    }

    Optional<Lancamento> buscaPor(String descricao) {
        return Optional.ofNullable(lancamentoRepositorio.findByDescricao(descricao));
    }

    public Lancamento buscaPor(Long codigo) {
    	return lancamentoRepositorio.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
    /*
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        return this.lancamentoRepositorio.salvar(lancamento);
    }
    */
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
    	Pessoa pessoa = pessoaServico.buscaPorCodigo(lancamento.getPessoa().getCodigo());
    	if (pessoa == null || pessoa.isInativo()) {
    		throw new PessoaInexistenteOuInativaException(); 
    	}
        return lancamentoRepositorio.save(lancamento);
    }

    @Transactional(readOnly = true)
    public List<Lancamento> buscaTodos() {
    	return this.lancamentoRepositorio.findAll();
    }
    
    public List<Lancamento> pesquisa(LancamentoFiltro filtro) {
        return lancamentoRepositorio.filtrar(filtro);
    }
    
    public Page<Lancamento> pesquisa(LancamentoFiltro filtro, Pageable pageable) {
        return lancamentoRepositorio.filtrar(filtro, pageable);
    }
    
    public Page<LancamentoResumo> resumir(LancamentoFiltro filtro, Pageable pageable) {
        return lancamentoRepositorio.resumir(filtro, pageable);
    }
    
    @Transactional
    public void excluir(Long codigo) {
    	this.lancamentoRepositorio.deleteById(codigo);
    }

    @Transactional
    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
    	Lancamento lancamentoSalva = this.buscaPor(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo" );
        this.salvar(lancamentoSalva);
        return lancamentoSalva;
    }
    
	public void atualizarPropriedadeDescricao(Long codigo, String descricao) {
		Lancamento lancamento = this.buscaPor(codigo);
		lancamento.setDescricao(descricao);
		this.salvar(lancamento);
	}
}
