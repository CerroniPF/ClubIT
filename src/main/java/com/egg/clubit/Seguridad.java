package com.egg.clubit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.egg.clubit.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter {
	@Autowired
	UsuarioServicio usuarioServicio;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/*")
			.permitAll()
		.and().formLogin()
			.loginPage("/login")
			.usernameParameter("mail")
			.passwordParameter("contrasena")
			.defaultSuccessUrl("/")
			.loginProcessingUrl("/logincheck")
			.failureUrl("/login?error=error")
			.permitAll()
		.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
		.and().csrf().disable();
	}
}
