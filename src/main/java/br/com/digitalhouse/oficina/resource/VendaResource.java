package br.com.digitalhouse.oficina.resource;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.digitalhouse.oficina.configuration.SwaggerPageable;
import br.com.digitalhouse.oficina.model.Venda;
import br.com.digitalhouse.oficina.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaResource {
	
	private VendaService vendaService;
	
	
	
	public VendaResource(VendaService vendaService) {
		this.vendaService = vendaService;
	}


	@PostMapping
	public ResponseEntity<Void> create( @RequestBody Venda venda){
		

		venda =  this.vendaService.create(venda);
	
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(venda.getId())
				 .toUri();	
		
		return ResponseEntity.created(uri).build();
		
	}
	
	
	@GetMapping
	@SwaggerPageable
	public ResponseEntity<Page<Venda>> findAll( Pageable pageable){
		return ResponseEntity.ok(this.vendaService.findAll(pageable));		
	}
	
	
	

}
