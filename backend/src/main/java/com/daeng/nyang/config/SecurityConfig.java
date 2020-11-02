package com.daeng.nyang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.daeng.nyang.jwt.JwtAuthenticationEntryPoint;
import com.daeng.nyang.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;

	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.
		addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).
		httpBasic().disable().	// 기본적으로 제공되는 loginForm() disable
		cors().disable().
		csrf().disable(). // 요청위조 방지 비활성화
		formLogin().disable().
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS). // 세션 사용 안함
		and().authorizeRequests().
			antMatchers("/newuser/**").anonymous().
			antMatchers("/user").hasAnyRole("ADMIN","USER").
			antMatchers("/admin").hasRole("ADMIN").
//			antMatchers("/newuser/**").permitAll().
			antMatchers("/newuser").permitAll().
		and().authorizeRequests().
			anyRequest(). // 어떤 요청이라도
			authenticated(). // 인증된 사용자만이 접근 허용
		and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
		
	}

//	@Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        // configure AuthenticationManager so that it knows from where to load
//        // user for matching credentials
//        // Use BCryptPasswordEncoder
//        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//    }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
}
