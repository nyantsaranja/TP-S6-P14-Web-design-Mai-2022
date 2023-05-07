package itu.s6.tpseo.framework.springutils.service;

import java.time.LocalDateTime;
import java.util.List;

import itu.s6.tpseo.framework.springutils.AuthenticatedDetails;
import itu.s6.tpseo.framework.springutils.LoginEntity;
import itu.s6.tpseo.framework.springutils.LoginRepo;
import itu.s6.tpseo.framework.springutils.exception.LoginException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

@Getter
@Setter

public abstract class LoginService<E extends LoginEntity, R extends LoginRepo<E>> implements ServiceLogin<E> {

    protected R repo;

    public LoginService(R repo) {
        this.repo = repo;
    }

    public AuthenticatedDetails<E> login(E entity) throws Exception {
        E usr = login(entity, repo);
        AuthenticatedDetails<E> details = buildDetails(usr);
        saveToken(details.getToken(), usr);
        return details;
    }

    public static  <E extends LoginEntity, R extends LoginRepo<E>> E login(E entity, R repo) throws LoginException {
        List<E> list = repo.findByEmail(entity.getEmail());
        if (list.size() == 0) throw new LoginException("user not found");
        for (E elt : list) {
            if (elt.getPassword().equals(entity.getPassword())) {
                return elt;
            }
        }
        throw new LoginException("wrong password");
    }

    public static <E extends LoginEntity> AuthenticatedDetails<E> buildDetails (E user) {
        String token = user.getEmail() + user.getPassword() + LocalDateTime.now();
        token = DigestUtils.sha1Hex(token.getBytes());
        return new AuthenticatedDetails<>(token, user);
    }

    @Override
    public E register(E entity) throws Exception {
        this.repo.save(entity);
        return entity;
    }
}
