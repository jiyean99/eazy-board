package com.beyond.basic.common.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Authentication 객체가 생성되고 난 이후에 동작하는 코드
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession httpSession = request.getSession();
        String role = null;
        for (GrantedAuthority g : authentication.getAuthorities()) {
            role = g.getAuthority();
            break;
        }
        httpSession.setAttribute("email", authentication.getName());
        httpSession.setAttribute("role", role);
        response.sendRedirect("/");
    }
}
