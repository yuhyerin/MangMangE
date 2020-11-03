package com.daeng.nyang.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    /** AuthenticationEntryPoint를 구현해서
     * 인증에 실패한 사용자의 response에 HttpServletResponse.SC_UNAUTHORIZED를 담아주도록
     * 구현한다.*/
    
    
    
    @Override
    public void commence(HttpServletRequest request, 
    					HttpServletResponse response,
                        AuthenticationException authException) 
                        		throws IOException, ServletException {
    	System.out.println("JwtAuthenticationEntryPoint - UnAuthorized");
    	
    	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//    	response.sendRedirect(request.getContextPath()+"/newuser/exception");
    	
    }
}