package itu.s6.tpseo.security;

import itu.s6.tpseo.framework.springutils.exception.CustomException;
import itu.s6.tpseo.model.token.Token;
import itu.s6.tpseo.service.AuthorLoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class Manager implements AuthenticationManager {
    private AuthorLoginService authorLoginService;

    public Manager(AuthorLoginService authorLoginService) {
        this.authorLoginService = authorLoginService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getPrincipal().toString();
        token = token.substring(7);
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            Token personToken = authorLoginService.isConnected(token);
            if (personToken != null) {
//                if (personToken.getAuthor().getPermission() > 0) {
//                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//                }
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                authentication = new UsernamePasswordAuthenticationToken(token, null, authorities);
            }
        } catch (CustomException e) {
            e.printStackTrace();
            return authentication;
        }
        return authentication;
    }
}
