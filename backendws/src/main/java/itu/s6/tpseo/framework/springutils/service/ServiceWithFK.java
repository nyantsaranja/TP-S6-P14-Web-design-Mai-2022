package itu.s6.tpseo.framework.springutils.service;


import itu.s6.tpseo.framework.springutils.util.ListResponse;

public interface ServiceWithFK<E, FK> extends Service<E> {
    String getFkName();
    ListResponse search (Object filter, Long fkId, Integer page) throws Exception;
}
