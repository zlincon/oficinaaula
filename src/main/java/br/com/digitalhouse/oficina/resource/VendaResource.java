package br.com.digitalhouse.oficina.resource;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.digitalhouse.oficina.model.Produto;
import br.com.digitalhouse.oficina.model.ProdutoVenda;
import br.com.digitalhouse.oficina.model.Venda;
import br.com.digitalhouse.oficina.repository.ProdutoRepository;
import br.com.digitalhouse.oficina.repository.ProdutoVendaRepository;
import br.com.digitalhouse.oficina.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaResource {
	
	private VendaRepository vendaRepository; // temporario: deve ser utilizado um service
	private ProdutoRepository produtoRepository;
	private ProdutoVendaRepository produtoVendaRepository;
	
	@Autowired
	public VendaResource(
			VendaRepository vendaRepository, 
			ProdutoRepository produtoRepository,
			ProdutoVendaRepository produtoVendaRepository
			) {
		this.produtoRepository = produtoRepository;
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository = produtoVendaRepository;
	}
	@PostMapping
	@Transactional
	public ResponseEntity<Void> create( @RequestBody Venda venda){
		
	
		
		
		Venda novaVenda =  this.vendaRepository.save(venda);
		
		for(ProdutoVenda pv :venda.getProdutos()) {
			pv.setVenda(novaVenda);
		}
		novaVenda.setProdutos(venda.getProdutos());
		this.produtoVendaRepository.saveAll(venda.getProdutos());
		
		URI uri = ServletUriComponentsBuilder
				 .fromCurrentRequest()
				 .path("/{id}")
				 .buildAndExpand(venda.getId())
				 .toUri();	
		
		return ResponseEntity.created(uri).build();
		
	}
	
	
	@GetMapping
	public ResponseEntity<List<Venda>> findAll(){
		return ResponseEntity.ok(this.vendaRepository.findAll());		
	}
	
	
	

}
