package com.matriculas.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ALUNO")) {
                response.sendRedirect("/aluno/matriculas");
                return;
            } else if (grantedAuthority.getAuthority().equals("ROLE_PROFESSOR")) {
                response.sendRedirect("/professor/alunos");
                return;
            } else if (grantedAuthority.getAuthority().equals("ROLE_SECRETARIA")) {
                response.sendRedirect("/");
                return;
            }
        }
    }
}
