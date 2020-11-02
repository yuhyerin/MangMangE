package com.test.abc.jwt;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.abc.dto.Account;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class JwtCustomFilter extends UsernamePasswordAuthenticationFilter {

	public JwtCustomFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
    		throws AuthenticationException{
        UsernamePasswordAuthenticationToken authRequest=null;
        try{
            Account userVO = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            authRequest = new UsernamePasswordAuthenticationToken(userVO.getUser_id(), userVO.getUser_password());
        } catch (IOException e){
            System.out.println(request.toString());
            System.out.println("IO Exception");
            e.printStackTrace();
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
