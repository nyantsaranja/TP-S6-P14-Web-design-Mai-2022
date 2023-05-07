package itu.s6.tpseo.framework.springutils.search;

public enum SearchOperator {

    min(">"),
    mineq(">="),
    eq("="),
    max("<"),
    maxeq("<="),
    like("like"),
    noteq("!="),
    ilike("iLike"),
    isnull(" is null"),
    isnotnull(" is not null"),

    or(" or ");


    public final String value;

    SearchOperator(String value) {
        this.value = value;
    }
}
