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
import com.example.financeiro.api.repositorio.PessoaRepositorio;
import com.example.financeiro.api.repositorio.filtro.LancamentoFiltro;
import com.example.financeiro.api.repositorio.filtro.PessoaFiltro;
import com.example.financeiro.api.servico.excecao.PessoaInexistenteOuInativaException;

@Service
public class PessoaServico {
	private final PessoaRepositorio pessoaRepositorio;

    @Autowired
    public PessoaServico(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    Optional<Pessoa> buscaPor(String nome) {
        return Optional.ofNullable(pessoaRepositorio.findByNome(nome));
    }

    public Pessoa buscaPor(Long codigo) {
    	return pessoaRepositorio.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
    
    public Pessoa buscaPorCodigo(Long codigo) {
    	return pessoaRepositorio.findById(codigo).orElseThrow(() -> new PessoaInexistenteOuInativaException());
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return this.pessoaRepositorio.save(pessoa);
    }

    @Transactional(readOnly = true)
    public List<Pessoa> buscaTodos() {
        return this.pessoaRepositorio.findAll();        
    }
    
    public List<Pessoa> pesquisa(PessoaFiltro filtro) {
        return pessoaRepositorio.filtrar(filtro);
    }
    
    public Page<Pessoa> pesquisa(PessoaFiltro filtro, Pageable pageable) {
        return pessoaRepositorio.filtrar(filtro, pageable);
    }
    
    @Transactional
    public void excluir(Long codigo) {
        this.pessoaRepositorio.deleteById(codigo);
    }

    @Transactional
    public Pessoa atualizar(Long codigo, Pessoa pessoa) {
    	Pessoa pessoaSalva = this.buscaPor(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo" );
        this.salvar(pessoaSalva);
        return pessoaSalva;
    }

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoa = this.buscaPor(codigo);
		pessoa.setAtivo(ativo);
		this.salvar(pessoa);
	}	
}
