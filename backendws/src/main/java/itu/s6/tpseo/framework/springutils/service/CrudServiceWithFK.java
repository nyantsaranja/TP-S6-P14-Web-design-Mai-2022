package itu.s6.tpseo.framework.springutils.service;

import java.util.Map;

import itu.s6.tpseo.framework.springutils.util.ListResponse;
import itu.s6.tpseo.framework.springutils.util.map.MapUtil;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityManager;

/*
*
*
*/
public abstract class CrudServiceWithFK<E, FK, FKR extends JpaRepository<FK, Long>, R extends JpaRepository<E, Long> > extends CrudService<E, R> implements ServiceWithFK<E, FK> {

    protected FKR fkRepo;

    public CrudServiceWithFK(R repo, EntityManager manager, FKR fkr) {
        super(repo, manager);
        this.fkRepo = fkr;
    }

    public ListResponse search (Object filter, Long fkId, Integer page) throws Exception {
        Map<String, Object> map = MapUtil.convert(filter);
        FK fk = this.fkRepo.findById(fkId).orElse(null);
        map.put(getFkName(), getFKValue(fk));
        return search(filter,map, page, 0);
    }

    public abstract Object getFKValue (FK fk);

}
