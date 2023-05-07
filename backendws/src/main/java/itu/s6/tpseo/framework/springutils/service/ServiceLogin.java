package itu.s6.tpseo.framework.springutils.service;


import itu.s6.tpseo.framework.springutils.AuthenticatedDetails;
import itu.s6.tpseo.framework.springutils.LoginEntity;
import itu.s6.tpseo.model.token.Token;

public interface ServiceLogin<E extends LoginEntity> {

    AuthenticatedDetails<E> login(E entity) throws Exception;
    Token isConnected (String token) throws Exception;
    void saveToken (String token, E owner) throws Exception;
    boolean logout (String token) throws Exception;
    E register(E entity) throws Exception;
    
}
