package com.example.financeiro.api.repositorio.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.financeiro.api.modelo.Lancamento;
import com.example.financeiro.api.repositorio.filtro.LancamentoFiltro;
import com.example.financeiro.api.repositorio.projecao.LancamentoResumo;

public class LancamentoRepositorioImpl implements LancamentoRepositorioQuery {
	@PersistenceContext
    private EntityManager manager;
	
	@Override
    public List<Lancamento> filtrar(LancamentoFiltro filtro) {
        // Select p From Lancamento p
        // 1 - Usamos o CriteriaBuilder(cb) para criar a CriteriaQuery (cq)
        // com a tipagem do tipo a ser selecionado (Lancamento)
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> cq = cb.createQuery(Lancamento.class);
        
        // 2 - clausula from e joins
        Root<Lancamento> lancamentoRoot = cq.from(Lancamento.class);

        // 3 - adiciona as restrições (os predicados) que serão passadas na clausula where
        Predicate[] restricoes = this.criaRestricoes(filtro, cb, lancamentoRoot);

        // 4 - monta a consulta com as restrições
        cq.select(lancamentoRoot).where(restricoes).orderBy(cb.asc(lancamentoRoot.get("descricao")));

        // 5 - cria e executa a consula
        return manager.createQuery(cq).getResultList();
    }
	
	private Predicate[] criaRestricoes(LancamentoFiltro filtro, CriteriaBuilder cb, Root<Lancamento> lancamentoRoot) {
		List<Predicate> predicates = new ArrayList<>();

	    if (!StringUtils.isEmpty(filtro.getDescricao())) {
	    	predicates.add(cb.like(lancamentoRoot.get("descricao"),"%" + filtro.getDescricao() + "%" ));
	    }

	    if (filtro.getDataVencimentoDe() != null) {
	        predicates.add(cb.greaterThanOrEqualTo(lancamentoRoot.get("dataVencimento"), filtro.getDataVencimentoDe()));
	    }

	    if (filtro.getDataVencimentoAte() != null) {
	        predicates.add(cb.lessThanOrEqualTo(lancamentoRoot.get("dataVencimento"), filtro.getDataVencimentoAte()));
	    }

	    return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	@Override
    public Page<Lancamento> filtrar(LancamentoFiltro filtro, Pageable pageable) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> cq = cb.createQuery(Lancamento.class);
        Root<Lancamento> lancamentoRoot = cq.from(Lancamento.class);
        Predicate[] restricoes = this.criaRestricoes(filtro, cb, lancamentoRoot);
        cq.where(restricoes);
        
        // Monta a consulta com as restrições de paginação
        TypedQuery<Lancamento> query = manager.createQuery(cq);
        adicionaRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filtro));
    }
	
	@Override
    public Page<LancamentoResumo> resumir(LancamentoFiltro filtro, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<LancamentoResumo> criteriaQuery = criteriaBuilder.createQuery(LancamentoResumo.class);
        Root<Lancamento> lancamentoRoot = criteriaQuery.from(Lancamento.class);
        
        criteriaQuery.select(criteriaBuilder.construct(LancamentoResumo.class, lancamentoRoot.get("codigo"),
        		lancamentoRoot.get("descricao"), lancamentoRoot.get("dataVencimento"), lancamentoRoot.get("dataPagamento"),
        		lancamentoRoot.get("valor"), lancamentoRoot.get("tipo"), lancamentoRoot.get("categoria").get("nome"),
        		lancamentoRoot.get("pessoa").get("nome")));
        
        Predicate[] restricoes = this.criaRestricoes(filtro, criteriaBuilder, lancamentoRoot);
        criteriaQuery.where(restricoes);
        
        // Monta a consulta com as restrições de paginação
        TypedQuery<LancamentoResumo> query = manager.createQuery(criteriaQuery);
        adicionaRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filtro));
    }

    private void adicionaRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        Integer paginaAtual = pageable.getPageNumber();
        Integer totalObjetosPorPagina = pageable.getPageSize();
        Integer primeiroObjetoDaPagina = paginaAtual * totalObjetosPorPagina;

        // 0 a n-1, n - (2n -1), ...
        query.setFirstResult(primeiroObjetoDaPagina);
        query.setMaxResults(totalObjetosPorPagina);
    }

    private Long total(LancamentoFiltro filtro) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Lancamento> lancamentoRoot = cq.from(Lancamento.class);
        Predicate[] restricoes = criaRestricoes(filtro, cb, lancamentoRoot);
        cq.where(restricoes);
        cq.select(cb.count(lancamentoRoot));

        return manager.createQuery(cq).getSingleResult();
    }
}
