package itu.s6.tpseo.framework.springutils.util;

public class ListResponse {

    public Object elements;
    public Long count;
    public Long page;
    public Integer pageSize;

    public ListResponse(Object elements, Long count, Long page, Integer pageSize) {
        this(elements, count);
        setPage(page);
        setPageSize(pageSize);
    }

    public ListResponse(Object elements, Long count) {
        setElements(elements);
        setCount(count);
    }

    public ListResponse() {
    }

    public Object getElements() {
        return elements;
    }

    public void setElements(Object elements) {
        this.elements = elements;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
