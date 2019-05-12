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
import com.example.financeiro.api.modelo.Categoria;
import com.example.financeiro.api.repositorio.CategoriaRepositorio;
import com.example.financeiro.api.servico.CategoriaServico;

@RestController
@RequestMapping("/categorias")
public class CategoriaControle {
	
	private final CategoriaServico categoriaServico;
	
	@Autowired
	private ApplicationEventPublisher publicar;
	
	@Autowired
    public CategoriaControle(CategoriaServico categoriaServico) {
        this.categoriaServico = categoriaServico;
    }
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<?> salvar(@Validated @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaServico.salvar(categoria);
        publicar.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

        return  ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<?> buscaTodos() {
        List<Categoria> categorias = categoriaServico.buscaTodos();

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(categorias);
        }
    }
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPorCodigo(@PathVariable Long codigo){
		Categoria categoria = categoriaServico.buscaPor(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ATUALIZAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @Validated @RequestBody Categoria categoria) {
        Categoria categoriaAtualizada = categoriaServico.atualizar(codigo, categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		categoriaServico.excluir(codigo);
	}
	/*
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepositorio.findAll();
	}
	*/
	/*
	@PostMapping
    public ResponseEntity<?> salvar(@Validated @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepositorio.save(categoria);
        publicar.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }
    */
	/*
	@PostMapping
	public ResponseEntity<Categoria> inserir(@Validated @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva = categoriaRepositorio.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	*/
	/*
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo){
		return categoriaRepositorio.findOne(codigo);
	}
	*/
	/*
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo){
		Categoria categoria = categoriaRepositorio.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	*/
	/*
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir (@PathVariable Long codigo){
		categoriaRepositorio.delete(codigo);
	}
	*/
}
