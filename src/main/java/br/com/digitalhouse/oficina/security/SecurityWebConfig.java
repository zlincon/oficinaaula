package br.com.digitalhouse.oficina.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.digitalhouse.oficina.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl usuarioService;

	@Autowired
	public SecurityWebConfig(UserDetailsServiceImpl usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/csrf", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
						"/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET, "/produtos").hasAnyRole("admin","code")
				.antMatchers(HttpMethod.POST, "/produtos").hasRole("admin")
				.antMatchers(HttpMethod.POST, "/usuarios").permitAll()
				.anyRequest().authenticated()

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().httpBasic()
				.and().cors().disable().csrf().disable(); 
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder());

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
