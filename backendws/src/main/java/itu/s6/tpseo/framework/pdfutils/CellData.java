package itu.s6.tpseo.framework.pdfutils;

import java.lang.reflect.Method;

public class CellData implements Comparable<CellData>{

    /**
     * in %
     */
    float width;
    String title;
    Method method;
    private Integer order;

    public CellData() {
    }

    public CellData(float width, String title, Method method, Integer order) {
        this.width = width;
        this.order = order;
        this.title = title;
        this.method = method;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(CellData cellData) {
        return order - cellData.order;
    }

}
