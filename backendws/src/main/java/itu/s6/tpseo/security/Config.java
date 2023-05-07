package itu.s6.tpseo.security;

import itu.s6.tpseo.service.AuthorLoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Component
public class Config {
    private AuthorLoginService authorLoginService;

    public Config(AuthorLoginService authorLoginService) {
        this.authorLoginService = authorLoginService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        PreFilter preFilter = new PreFilter(authorLoginService);
        httpSecurity.cors().configurationSource(corsConfig()).and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .cors()
                .and()
                .addFilter(preFilter)
                .authorizeHttpRequests(
                        (auhtz) ->
                                auhtz
                                        .requestMatchers(HttpMethod.POST,"/author/login").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/article","/article/{id}").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/author","/article").hasAuthority("ROLE_USER")
                                        .requestMatchers(HttpMethod.GET, "/author","/author/{id}").hasAuthority("ROLE_USER")
                                        .requestMatchers(HttpMethod.PUT, "/author/{id}","/article/{id}").hasAuthority("ROLE_USER")
                                        .requestMatchers(HttpMethod.DELETE, "/author/{id}","/article/{id}").hasAuthority("ROLE_USER")
                ).httpBasic();
        return httpSecurity.build();
    }

    public CorsConfigurationSource corsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedOrigins(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
