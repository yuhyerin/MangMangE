package com.test.abc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.test.abc.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		cors().disable().
		csrf().disable().
		formLogin().disable().
		headers().frameOptions().disable();
//		http.
//		addFilter(jwtRequestFilter).
//		authorizeRequests().
//		antMatchers("/newuser/**").anonymous().
//		antMatchers("/admin/**").hasRole("ADMIN").
//		antMatchers("/user/**").permitAll().
//		anyRequest().authenticated();
		
		; //header 충돌 방지(http 내부 header)
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	

}
