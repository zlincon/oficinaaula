package br.com.digitalhouse.oficina.resource;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.digitalhouse.oficina.model.Produto;
import br.com.digitalhouse.oficina.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	private ProdutoRepository produtoRepository; // temporario: deve ser utilizado um service

	public ProdutoResource(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	@PostMapping
	public ResponseEntity<Void> create( @RequestBody Produto produto){
		
		produto = this.produtoRepository.save(produto);
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(produto.getId())
				 .toUri();
		
		
		return ResponseEntity.created(uri).build();
		
	}
	
	
	
	@GetMapping
	public ResponseEntity<List<Produto>> findAll(){
		return ResponseEntity.ok(this.produtoRepository.findAll());		
	}
	
	
	

}
