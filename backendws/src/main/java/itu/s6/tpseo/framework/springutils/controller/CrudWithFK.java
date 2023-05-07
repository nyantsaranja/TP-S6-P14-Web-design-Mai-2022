package itu.s6.tpseo.framework.springutils.controller;

import itu.s6.tpseo.framework.springutils.service.Service;
import itu.s6.tpseo.framework.springutils.service.ServiceWithFK;
import itu.s6.tpseo.framework.springutils.util.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import itu.s6.tpseo.framework.springutils.model.HasFK;

import static itu.s6.tpseo.framework.springutils.util.ControllerUtil.returnSuccess;

/*
* How to use:
*   1- Create a controller class that extends this class
*   2- create a service that extends CrudServiceWithFK
*   3- Add @RequestMapping("something/{fkId}/something") annotation to the class
* Then you are good for CRUD operations
*
* use case example:
*   "/books/{fkId}/chapters"
*
* */

public class CrudWithFK <FK, FKS extends Service<FK>, E extends HasFK<FK>, S extends ServiceWithFK<E, FK>, C> {

    protected final S service;
    protected final FKS fkService;

    public CrudWithFK(S service, FKS fkService) {
        this.service = service;
        this.fkService = fkService;
    }

    @PostMapping("")
    public ResponseEntity<SuccessResponse> create (@PathVariable Long fkId, @RequestBody E obj) throws Exception {
        FK fk = this.fkService.findById(fkId);
        obj.setFK(fk);
        return returnSuccess(service.create(obj), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable("id") Long id) throws Exception {
        return returnSuccess(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return returnSuccess("", HttpStatus.NO_CONTENT);
    }

    @GetMapping("")
    public ResponseEntity<SuccessResponse> findAll(@PathVariable Long fkId, C filter, @RequestParam(required = false) Integer page) throws Exception {
        return returnSuccess(service.search(filter, fkId, page), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long fkId, @PathVariable("id") Long id, @RequestBody E obj) throws Exception {
        FK fk = this.fkService.findById(fkId);
        obj.setId(id);
        obj.setFK(fk);
        return returnSuccess(service.update(obj), HttpStatus.OK);
    }

}
