package itu.s6.tpseo.framework.springutils.service;

import itu.s6.tpseo.framework.springutils.util.ListResponse;

import java.util.List;


public interface Service <E> {

    ListResponse search (Object filter, Integer page,Object order) throws Exception;

    int getPageSize() throws Exception;

    List<E> findAll(int page) throws Exception;

    E create(E obj) throws Exception;

    E update(E obj) throws Exception;

    void delete(Long id) throws Exception;

    E findById(Long id) throws Exception;

    Iterable<E> findAll() throws Exception;

    List<E> saveAll(List<E> obj) throws Exception;

    byte[] export(Object filter, Integer page, Object order,Class<?> classObj) throws Exception;

}
