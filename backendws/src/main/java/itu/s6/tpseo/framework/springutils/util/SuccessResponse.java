package itu.s6.tpseo.framework.springutils.util;

public class SuccessResponse {
    private Object data;

    public SuccessResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
