package itu.s6.tpseo.framework.springutils.search;

public class FieldOperator {
    public final String field;
    public final SearchOperator operator;

    public FieldOperator(String field, SearchOperator operator) {
        this.field = field;
        this.operator = operator;
    }

   public static FieldOperator construct(String key, Object val) {
        String[] keys = key.split("_");
        SearchOperator operator;
        StringBuilder field;
        try {
            operator = SearchOperator.valueOf(keys[0]);
            field = new StringBuilder(keys[1]);
            for (int j = 2; j < keys.length; j++) {
                field.append("_").append(keys[j]);
            }
        }
        catch (IllegalArgumentException e) {
            operator = SearchOperator.eq;
            field = new StringBuilder(key);
        }
        if (operator == SearchOperator.isnull && !((Boolean) val)) {
            operator = SearchOperator.isnotnull;
        }
        else if (operator == SearchOperator.isnotnull && !((Boolean) val)) {
            operator = SearchOperator.isnull;
        }
        return new FieldOperator(field.toString(), operator);
    }
}
