package br.com.digitalhouse.oficina.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.digitalhouse.oficina.model.ProdutoVenda;
import br.com.digitalhouse.oficina.model.Venda;
import br.com.digitalhouse.oficina.repository.ProdutoVendaRepository;
import br.com.digitalhouse.oficina.repository.VendaRepository;

@Service
public class VendaService {
	
	private final VendaRepository vendaRepository;
	private final ProdutoVendaRepository produtoVendaRepository;
	
	@Autowired
	public VendaService(
			VendaRepository vendaRepository,
			ProdutoVendaRepository produtoVendaRepository ) {
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository  = produtoVendaRepository;
	}
	
	@Transactional
	public Venda create(Venda venda) {
		venda.setId(null);
		Venda novaVenda = this.vendaRepository.save(venda);
		for(ProdutoVenda pv :venda.getProdutos()) {
			pv.setVenda(novaVenda);
		}
		novaVenda.setProdutos(venda.getProdutos());
		
		this.produtoVendaRepository.saveAll(venda.getProdutos());
		
		return novaVenda;
	}
	
	public Venda update(Venda novo) {

		return this.vendaRepository.save(novo);
		
	}
	 
	
	public Venda findById(Long id) {
		Optional
			.ofNullable(id)
			.orElseThrow( () -> new RuntimeException("O id não pode ser nulo"));  // todo: criar exceção personalizada para argumento ilegal
		
		return this.vendaRepository.findById(id)
				.orElseThrow( () -> new RuntimeException("Não foi possivel encontrar um objeto com id " + id)); // todo: mudar pra object not found exception
	}
	
	public Page<Venda> findAll(Pageable pageable){
		return this.vendaRepository.findAll(pageable);
	}
	
	public void deleteById(Long id) {
		this.findById(id);
		
		this.vendaRepository.deleteById(id);
	}
	
}

