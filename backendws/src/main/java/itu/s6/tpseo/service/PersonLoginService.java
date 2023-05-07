package itu.s6.tpseo.service;

import itu.s6.tpseo.framework.springutils.LoginRepo;
import itu.s6.tpseo.framework.springutils.exception.CustomException;
import itu.s6.tpseo.framework.springutils.service.LoginService;
import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.model.Person;
import itu.s6.tpseo.model.token.Token;
import itu.s6.tpseo.repository.TokenRepository;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class PersonLoginService<E extends Person, R extends LoginRepo<E>> extends LoginService<E, R> {
    TokenRepository tokenRepo;

    public PersonLoginService(R repo, TokenRepository tokenRepo) {
        super(repo);
        this.tokenRepo = tokenRepo;
    }

    @Override
    public Token isConnected(String s) throws CustomException {
        Token token = this.tokenRepo.findByTokenValue(s);
        if (token == null) {
            throw new CustomException("invalid token");
        }
        return token;
    }

    @Override
    public void saveToken(String s, E e) {
        Token token = new Token();
        token.setTokenValue(s);
        token.setAuthor((Author) e);
        token.setExpiration_date(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        this.tokenRepo.save(token);
//        this.tokenRepo.save(new Token(s, e.getRole()));
    }

    @Override
    public boolean logout(String s) throws CustomException {
        Token token = this.tokenRepo.findByTokenValue(s);
        if (token == null) {
            throw new CustomException("You're not connected");
        }
        this.tokenRepo.delete(token);
        return true;
    }
}
