package com.test.abc.config;

<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

<<<<<<< HEAD
=======
import com.test.abc.jwt.JwtRequestFilter;

>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
<<<<<<< HEAD
=======
	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		cors().disable().
		csrf().disable().
		formLogin().disable().
<<<<<<< HEAD
		headers().frameOptions().disable(); //header 충돌 방지(http 내부 header)
=======
		headers().frameOptions().disable();
//		http.
//		addFilter(jwtRequestFilter).
//		authorizeRequests().
//		antMatchers("/newuser/**").anonymous().
//		antMatchers("/admin/**").hasRole("ADMIN").
//		antMatchers("/user/**").permitAll().
//		anyRequest().authenticated();
		
		; //header 충돌 방지(http 내부 header)
>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	

}
