package itu.s6.tpseo.framework.springutils.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import itu.s6.tpseo.framework.pdfutils.PdfGenerator;
import itu.s6.tpseo.framework.springutils.search.Condition;
import itu.s6.tpseo.framework.springutils.search.FieldOperator;
import itu.s6.tpseo.framework.springutils.search.SearchOperator;
import itu.s6.tpseo.framework.springutils.util.ListResponse;
import itu.s6.tpseo.framework.springutils.util.map.MapUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public abstract class CrudService<E, R extends JpaRepository<E, Long>> implements Service<E> {

    protected final R repo;

    protected final EntityManager manager;

    public CrudService(R repo, EntityManager manager) {
        this.repo = repo;
        this.manager = manager;
    }

    public abstract Class<E> getEntityClass();

    @Override
    public ListResponse search(Object filter, Integer page, Object order) throws Exception {
        return search(filter, MapUtil.convert(filter), page, order);
    }

    protected ListResponse search(Object filter, Map<String, Object> criteria, Integer page, Class<?> source, Object order) throws Exception {
        StringBuilder query = new StringBuilder(" where true");
        List<Object> params = new ArrayList<>();


        int i = 1;
        for (String key : criteria.keySet()) {
            Object val = criteria.get(key);
            if (val == null) {
                continue;
            }
            FieldOperator op = FieldOperator.construct(key, val);
            Object index = "?" + i;
            if (op.operator == SearchOperator.isnotnull || op.operator == SearchOperator.isnull) {
                index = "";
            } else if (op.operator == SearchOperator.or) {
                continue;
            } else {
                params.add(val);
            }
            query.append(String.format(" and u.%s %s %s", op.field, op.operator.value, index));
            i++;
        }


        List<Condition> conditions = getAdditionalConditionFrom(filter);
        for (Condition cond : conditions) {
            if (cond.getCondition() != null) {
                query.append(cond.getCondition());
            }
        }
        String orderByClause = "";
        if (order != null) {
            String[] orderArray = order.toString().split(",");
            if (orderArray.length == 2) {
                orderByClause = " order by " + orderArray[0] + " " + (orderArray[1].equals("0") ? "asc" : "desc");
            }else {
                throw new Exception("Invalid order by clause");
            }
        }

        Query q = manager.createQuery("select u from " + source.getSimpleName() + " u " + query.toString()+orderByClause, getEntityClass());
        Query c = manager.createQuery("select count(u.id) from " + source.getSimpleName() + " u " + query.toString(), Long.class);

        populateQuery(params, conditions, q, c);

        ListResponse response = new ListResponse();

        if (page != null) {
            q.setFirstResult((page - 1) * getPageSize());
            q.setMaxResults(getPageSize());
        }

        response.setElements(q.getResultList());
        response.setCount((Long) c.getResultList().get(0));

        if (page != null) {
            response.setPage(page.longValue());
            response.setPageSize(getPageSize());
        } else {
            response.setPageSize(response.getCount().intValue());
            response.setPage(1L);
        }
        return response;
    }

    @Override
    public byte[] export(Object filter, Integer page, Object order,Class<?> classObj) throws Exception {
        ListResponse response = search(filter, page, order);
        List<?> elements = (List<?>) response.getElements();
        return PdfGenerator.createPDF(elements,classObj.getName()+".pdf","Liste des "+ classObj.getSimpleName()+"s",classObj);
    }

    public List<Condition> getAdditionalConditionFrom(Object filter) throws Exception {
        ArrayList<Condition> conditions = new ArrayList<>();
        Field[] fields = filter.getClass().getDeclaredFields();
        // loop on all fields and do something on field with name starting with or_
        for (Field field : fields) {
            if (field.getName().startsWith("or_")) {
                field.setAccessible(true);
                Object value = field.get(filter);
                // remove /* and */ from the value
                if (value != null) {
                    value = value.toString().replace("/*", "").replace("*/", "");
                    // loop on all values and add a condition to the list
                    Condition condition = new Condition();
                    String orConditions = " and " + field.getName().substring(3) + " in (" + value + ")";
                    condition.setCondition(orConditions);
                    conditions.add(condition);
                }
            }
        }
        return conditions;
    }

    protected ListResponse search(Object filter, Map<String, Object> criteria, Integer page, Object order) throws Exception {
        return search(filter, criteria, page, getEntityClass(), order);
    }

    public String getClassName() {
        return getEntityClass().getSimpleName();
    }

    private void populateQuery(List<Object> params, List<Condition> conditions, Query... query) {
        for (int i = 0; i < params.size(); i++) {
            for (Query q : query) {
                q.setParameter(i + 1, params.get(i));
            }
        }

        for (Condition condition : conditions) {
            for (Query q : query) {
                if (condition.getValue() != null) {
                    q.setParameter(condition.getName(), condition.getValue());
                }
            }
        }
    }

    @Override
    public List<E> findAll(int page) throws Exception {
        return repo.findAll(Pageable.ofSize(getPageSize()).withPage(page)).getContent();
    }

    @Override
    public E create(E obj) throws Exception {
        return repo.save(obj);
    }

    @Override
    public E update(E obj) throws Exception {
        return repo.save(obj);
    }

    @Override
    public void delete(Long id) throws Exception {
        repo.deleteById(id);
    }

    @Override
    public E findById(Long id) throws Exception {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Iterable<E> findAll() throws Exception {
        return repo.findAll();
    }

    @Override
    public List<E> saveAll(List<E> iterable) throws Exception {
        return repo.saveAll(iterable);
    }

    @Override
    public int getPageSize() {
        return 5;
    }
}

