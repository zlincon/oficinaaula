package br.com.digitalhouse.oficina.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.digitalhouse.oficina.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private Set<? extends GrantedAuthority> roles = new HashSet<>();
	
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(String username, String password, Set<? extends GrantedAuthority> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public UserDetailsImpl(Usuario usuario) {
		this(
				usuario.getUsername(),
				usuario.getSenha(),
				usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toSet())
			);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
