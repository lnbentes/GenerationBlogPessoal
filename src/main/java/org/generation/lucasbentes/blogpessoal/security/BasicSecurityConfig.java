package org.generation.lucasbentes.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity  // Abilita a configuracao de seguranca
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailServece;
	
	@Override  // Contruindo um usuario em memoria padrao para fazer o testes
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailServece);

		auth.inMemoryAuthentication()
		.withUser("root")  // Escolher um nome de user padrao
		.password(passwordEncoder().encode("root"))  // Uma senha padrao
		.authorities("ROLE_USER"); // Serve para o programa entender que e um usuario
		
	}  // Quando for passar para o front temos que apadar esse metodo pois se nao vira um backdoor
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder(){  // Fazer a encriptação da senha
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{  // Criando uma porta de acesso atraves de cadastro e loging
		
		http.authorizeRequests()
		.antMatchers("/usuario/logar").permitAll()  // Se o logar for ok vai permitir todos acessos
		.antMatchers("/usuario/cadastrar").permitAll()  // Se o cadastro for criado vai permitir todos acessos
		.antMatchers("/usuario/all").permitAll()
		.antMatchers("/usuario/atualizar").permitAll()  // Permitir que o usuario modifique os dados
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		.and().csrf().disable();
		
	}
	
}
