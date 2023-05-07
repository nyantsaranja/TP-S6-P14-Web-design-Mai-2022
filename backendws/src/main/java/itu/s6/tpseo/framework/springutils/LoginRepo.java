package itu.s6.tpseo.framework.springutils;

import java.util.List;

public interface LoginRepo<E extends LoginEntity> {
    List<E> findByEmail (String email);
    E save (E entity);
}
