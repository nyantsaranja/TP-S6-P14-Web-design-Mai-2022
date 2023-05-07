package itu.s6.tpseo.service;

import itu.s6.tpseo.framework.springutils.service.ServiceLogin;
import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.repository.AuthorRepository;
import itu.s6.tpseo.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorLoginService extends PersonLoginService<Author, AuthorRepository> implements ServiceLogin<Author> {
    public AuthorLoginService(AuthorRepository repo, TokenRepository tokenRepo) {
        super(repo, tokenRepo);
    }
}
