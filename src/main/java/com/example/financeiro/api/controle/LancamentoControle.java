package com.example.financeiro.api.controle;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.example.financeiro.api.controle.evento.RecursoCriadoEvent;
import com.example.financeiro.api.modelo.Lancamento;
import com.example.financeiro.api.repositorio.filtro.LancamentoFiltro;
import com.example.financeiro.api.repositorio.projecao.LancamentoResumo;
import com.example.financeiro.api.servico.LancamentoServico;
import com.example.financeiro.api.servico.excecao.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoControle {
	
	private final LancamentoServico lancamentoServico;
	
	@Autowired
	private ApplicationEventPublisher publicar;
	
	@Autowired
    public LancamentoControle(LancamentoServico lancamentoServico) {
        this.lancamentoServico = lancamentoServico;
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<?> salvar(@Validated @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = lancamentoServico.salvar(lancamento);
        publicar.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));

        return  ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }	
	/*
	@GetMapping
    public ResponseEntity<?> buscaTodos() {
        List<Lancamento> lancamentos = lancamentoServico.buscaTodos();

        if (lancamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(lancamentos);
        }
    }
	*/
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public List<Lancamento> pesquisar(LancamentoFiltro filtro) {
        return lancamentoServico.pesquisa(filtro);
    }
	
	@GetMapping(params = "resumo")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<LancamentoResumo> resumir(LancamentoFiltro filtro, Pageable pageable) {
        return lancamentoServico.resumir(filtro, pageable);
    }
	
	@GetMapping("/paginacao")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisarComPaginacao(LancamentoFiltro filtro, Pageable pageable) {
        return lancamentoServico.pesquisa(filtro, pageable);
    }
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo){
		Lancamento lancamento = lancamentoServico.buscaPor(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Validated @RequestBody Lancamento lancamento) {
        Lancamento lancamentoAtualizada = lancamentoServico.atualizar(codigo, lancamento);
        return ResponseEntity.ok(lancamentoAtualizada);
    }
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		lancamentoServico.excluir(codigo);
	}
}
