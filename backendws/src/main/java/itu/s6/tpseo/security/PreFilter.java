package itu.s6.tpseo.security;

import itu.s6.tpseo.service.AuthorLoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreFilter extends AbstractPreAuthenticatedProcessingFilter {
    public PreFilter(AuthorLoginService authorLoginService) {
        Manager manager = new Manager(authorLoginService);
        super.setAuthenticationManager(manager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }
}
