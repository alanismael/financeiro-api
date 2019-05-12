package com.example.financeiro.api.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.financeiro.api.modelo.Categoria;
import com.example.financeiro.api.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServico {
	private final CategoriaRepositorio categoriaRepositorio;
    private final GenericoServico<Categoria> genericoServico;

    @Autowired
    public CategoriaServico(CategoriaRepositorio categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.genericoServico = new GenericoServico<Categoria>(categoriaRepositorio);
    }

    Optional<Categoria> buscaPor(String nome) {
        return Optional.ofNullable(categoriaRepositorio.findByNome(nome));
    }

    public Categoria buscaPor(Long codigo) {
        return this.genericoServico.buscaPor(codigo);
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        return this.genericoServico.salvar(categoria);
    }

    @Transactional(readOnly = true)
    public List<Categoria> buscaTodos() {
        return this.genericoServico.buscaTodos();
    }

    @Transactional
    public void excluir(Long codigo) {
        this.genericoServico.excluir(codigo);
    }

    @Transactional
    public Categoria atualizar(Long codigo, Categoria categoria) {
        return this.genericoServico.atualizar(categoria, codigo);
    }
}
