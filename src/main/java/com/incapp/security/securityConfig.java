package com.incapp.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import com.incapp.entity.my_user;
import com.incapp.service.service;

import static org.springframework.security.config.Customizer.withDefaults;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Configuration
@EnableWebSecurity
public class securityConfig {
	
	@Autowired
	service serv;
	
	
//	Security filter Chain
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
		https.authorizeHttpRequests(
					
					(requests) -> requests	
										.requestMatchers("/","/register","/registerUser","/loginPage","/login").permitAll()
										.requestMatchers("/adminOnly").hasRole("ADMIN")
										.requestMatchers("/userOnly").hasRole("USER")
										.requestMatchers("/any").hasAnyRole("USER","ADMIN")
										.anyRequest().authenticated()
										)
		
//									.formLogin(withDefaults())
//									import static org.springframework.security.config.Customizer.withDefaults;
									
									.formLogin(login -> login
															.loginPage("/loginPage")
															.loginProcessingUrl("/login")
															.defaultSuccessUrl("/",true)
															.permitAll()
											)

									
									.logout((logout)->logout.logoutSuccessUrl("/").permitAll())
									.exceptionHandling(handeling -> handeling.accessDeniedPage("/accessDenied"))
									;
				
				return https.build();
	}
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailService() {
//		UserDetails u1 = User.withDefaultPasswordEncoder().username("aa").password("aaaa").roles("USER").build();
//		UserDetails u2 = User.withDefaultPasswordEncoder().username("bb").password("bbbb").roles("ADMIN").build();
////		UserDetails u3 = User.withDefaultPasswordEncoder().usernam
//		return new InMemoryUserDetailsManager(u1,u2);
//	}
	
	
//	DB Authentication 
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
//		using anonymous Class
		UserDetailsService CustomUDS = new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				my_user u = serv.findByEmail(email);
				if (u == null) {
					throw new UsernameNotFoundException("User Not Found!");
				}
				return new User(
						u.getEmail(),
						u.getPassword(),
						true,
						true,
						true,
						true,
						List.of(new SimpleGrantedAuthority(u.getRole()))
				);
			}
		};
		
		
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(CustomUDS);
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  // for no password encoding
		provider.setPasswordEncoder(serv.getPasswordEncoder());   //for password encoding 
		
		
		return provider;
		
	}
	
}


