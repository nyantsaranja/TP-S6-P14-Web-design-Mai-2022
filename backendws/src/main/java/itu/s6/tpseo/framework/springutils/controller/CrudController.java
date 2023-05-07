package itu.s6.tpseo.framework.springutils.controller;

import itu.s6.tpseo.framework.springutils.service.Service;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import itu.s6.tpseo.framework.springutils.model.HasId;

import static itu.s6.tpseo.framework.springutils.util.ControllerUtil.returnSuccess;


/*
* How to use:
*   1- Create a controller class that extends this class
*   2- create a service that extends CrudService
*   3- Add @RequestMapping annotation to the class
* Then you are good for CRUD operations
* */

public class CrudController<E extends HasId, S extends Service<E>, C> {

    protected final S service;
    protected Class<E> entityClass;

    public CrudController(S service, Class<E> entityClass) {
        this.service = service;
        this.entityClass = entityClass;
    }

    public CrudController(S service) {
        this.service = service;
    }



    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody E obj) throws Exception {
        return returnSuccess(service.create(obj), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws Exception {
        return returnSuccess(service.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(C filter, @RequestParam(required = false) Integer page,@RequestParam(required = false) Object order) throws Exception {
        return returnSuccess(service.search(filter, page,order), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody E obj) throws Exception {
        obj.setId(id);
        return returnSuccess(service.update(obj), HttpStatus.OK);
    }

    @GetMapping("/pdf")
    public void getPdf(C filter, @RequestParam(required = false) Integer page, @RequestParam(required = false) Object order, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=export.pdf");
        response.setContentType("application/pdf");
        byte[] pdfContent= service.export(filter,page,order,entityClass);
        response.setContentLength(pdfContent.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pdfContent);
        outputStream.flush();
    }
}
