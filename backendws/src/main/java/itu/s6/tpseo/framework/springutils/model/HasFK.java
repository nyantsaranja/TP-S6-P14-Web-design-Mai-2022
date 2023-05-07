package itu.s6.tpseo.framework.springutils.model;


import itu.s6.tpseo.framework.springutils.exception.CustomException;

public abstract class HasFK<FK> extends HasId {
    public abstract void setFK(FK fk) throws CustomException;
}
