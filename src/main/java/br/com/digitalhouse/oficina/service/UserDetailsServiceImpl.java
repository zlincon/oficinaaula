package br.com.digitalhouse.oficina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.digitalhouse.oficina.model.Usuario;
import br.com.digitalhouse.oficina.repository.UsuarioRepository;
import br.com.digitalhouse.oficina.security.UserDetailsImpl;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private UsuarioRepository usuarioRepository;

	
	@Autowired
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = this.usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
		return  new UserDetailsImpl(usuario);
	}
	
}
