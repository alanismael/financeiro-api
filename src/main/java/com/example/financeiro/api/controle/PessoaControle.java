package com.example.financeiro.api.controle;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.financeiro.api.controle.evento.RecursoCriadoEvent;
import com.example.financeiro.api.modelo.Pessoa;
import com.example.financeiro.api.modelo.Pessoa;
import com.example.financeiro.api.repositorio.PessoaRepositorio;
import com.example.financeiro.api.servico.PessoaServico;
import com.example.financeiro.api.servico.PessoaServico;

@RestController
@RequestMapping("/pessoas")
public class PessoaControle {
	
	private final PessoaServico pessoaServico;
	
	@Autowired
	private ApplicationEventPublisher publicar;
	
	@Autowired
    public PessoaControle(PessoaServico pessoaServico) {
        this.pessoaServico = pessoaServico;
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<?> salvar(@Validated @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaServico.salvar(pessoa);
        publicar.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

        return  ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<?> buscaTodos() {
        List<Pessoa> pessoas = pessoaServico.buscaTodos();

        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pessoas);
        }
    }
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo){
		Pessoa pessoa = pessoaServico.buscaPor(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Validated @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaServico.atualizar(codigo, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }
	
	@PutMapping("/{codigo}/ativo")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_PESSOA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
        pessoaServico.atualizarPropriedadeAtivo(codigo, ativo);
    }
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		pessoaServico.excluir(codigo);
	}
	/*
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepositorio.findAll();
	}
	*/
	/*
	@PostMapping
    public ResponseEntity<?> salvar(@Validated @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepositorio.save(pessoa);
        publicar.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        
        return  ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }
    */
	/*
	@PostMapping
	public ResponseEntity<Pessoa> inserir(@Validated @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaSalva = pessoaRepositorio.save(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	*/
	/*
	@GetMapping("/{codigo}")
	public Pessoa buscarPeloCodigo(@PathVariable Long codigo){
		return pessoaRepositorio.findOne(codigo);
	}
	*/
	/*
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Pessoa pessoa = pessoaRepositorio.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	*/
	/*
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir (@PathVariable Long codigo){
		pessoaRepositorio.delete(codigo);
	}
	*/
}
