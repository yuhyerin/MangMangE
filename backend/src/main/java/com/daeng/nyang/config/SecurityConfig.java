package com.daeng.nyang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.daeng.nyang.jwt.JwtAuthenticationEntryPoint;
import com.daeng.nyang.jwt.JwtRequestFilter;
import com.daeng.nyang.service.user.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**
		 * 일반적인 로직 말고, 블로그에서 구현한 로직
		 * 1) JwtRequestFilter 에서 유효한 토큰을 가지고 있는지 먼저 체크한다. 
		 * */
		
		http
			.httpBasic().disable()	// rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
			.cors().disable()		
			.csrf().disable()		// rest api이므로 csrf 보안이 필요없으므로 disable처리. // 요청위조 방지 비활성화
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
			.and().authorizeRequests()
						.antMatchers("/newuser/**").permitAll()
						.antMatchers("/admin/**").hasAnyRole("ADMIN")
						.anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 가능합니다.
						.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // jwt token 필터를  id/password 인증 필터 전에 넣어라.
		
		
	}

	/** 비밀번호 암호화 해서 저장하기 위해 등록 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/** jwtUserDetailService가 PasswordEncoder를 사용할 수 있게 등록해 줘야 한다. */
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/**");
	}
}
