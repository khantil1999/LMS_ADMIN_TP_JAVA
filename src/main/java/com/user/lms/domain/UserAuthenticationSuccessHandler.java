package com.user.lms.domain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        authentication.getAuthorities().forEach(grantedAuthority -> {
            System.out.println(grantedAuthority.getAuthority());
        });
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/home");
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("TRUCK_PROVIDER"))) {
            response.sendRedirect("/dashboardTP");
        } else {
            response.sendRedirect("/default-home");
        }
    }

}
