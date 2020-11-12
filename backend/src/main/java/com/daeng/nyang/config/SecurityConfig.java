package com.daeng.nyang.config;

import java.util.Arrays;

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
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


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
   
   final String corsOrigin = "*";

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
      .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).
      httpBasic().disable().   // 기본적으로 제공되는 loginForm() disable
      csrf().disable(). // 요청위조 방지 비활성화
      cors().and().
      formLogin().disable().
      sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS). // 세션 사용 안함
      and().authorizeRequests().
         requestMatchers(CorsUtils::isPreFlightRequest).permitAll().
         antMatchers("/newuser/login","/newuser/signup").anonymous().
         antMatchers("/user").hasAnyRole("ADMIN","USER").
         antMatchers("/admin").hasRole("ADMIN").
         antMatchers("/newuser/**").permitAll().
//         antMatchers("/newuser").permitAll().
      and().authorizeRequests().
         anyRequest(). // 어떤 요청이라도
         authenticated(). // 인증된 사용자만이 접근 허용
      and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
      
   }
   
   
   public CorsConfigurationSource corsConfigurationSource(String corsOrigin) {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("*");
//	    configuration.setAllowedOrigins(Arrays.asList(corsOrigin));
	    configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD","OPTIONS","PUT","PATCH","DELETE"));
	    configuration.setMaxAge(10L);
	    configuration.setAllowCredentials(true);
	    configuration.setAllowedHeaders(Arrays.asList("Accept","Access-Control-Request-Method","Access-Control-Request-Headers",
	      "Accept-Language","Authorization","Content-Type","Request-Name","Request-Surname","Origin","X-Request-AppVersion",
	      "X-Request-OsVersion", "X-Request-Device", "X-Requested-With"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    
	    
	    
		/*
		 * configuration.addAllowedOrigin("*"); configuration.addAllowedMethod("*");
		 * configuration.addAllowedHeader("*"); UrlBasedCorsConfigurationSource source =
		 * new UrlBasedCorsConfigurationSource();
		 * source.registerCorsConfiguration("/**", configuration);
		 */
	    return source;
	}
   


//   @Autowired
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
   
//   @Bean
//   public MultipartResolver multipartResolver() {
//       CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//       multipartResolver.setMaxUploadSize(2000000000);
//       return multipartResolver;
//   }
}