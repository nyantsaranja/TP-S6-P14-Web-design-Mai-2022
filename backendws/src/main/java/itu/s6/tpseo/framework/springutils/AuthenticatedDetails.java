package itu.s6.tpseo.framework.springutils;

public class AuthenticatedDetails<T> {

    private String token;
    private T entity;

    public AuthenticatedDetails(String token, T entity) {
        setToken(token);
        setEntity(entity);
    }

    public AuthenticatedDetails() {
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
