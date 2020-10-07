package br.com.digitalhouse.oficina.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.digitalhouse.oficina.service.UsuarioService;

@EnableWebSecurity
public class SecurityWebConfig  extends WebSecurityConfigurerAdapter{
	
	private UsuarioService usuarioService;
	
	public SecurityWebConfig(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		    .csrf().disable()
		   .authorizeRequests()
		   
		    .antMatchers("/login/").permitAll()
		    .antMatchers("/webjars/**").permitAll()
		    .antMatchers("/resources/**").permitAll()

		    .antMatchers("/swagger-ui.html").permitAll()
		    .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
		   	.anyRequest().authenticated()
		   .and()
		   .formLogin()
		   .and()
		   .httpBasic();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception{
		builder
		    .userDetailsService(usuarioService)
		    .passwordEncoder( new BCryptPasswordEncoder());
		
	}
}
