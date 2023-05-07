package itu.s6.tpseo.controller;

import itu.s6.tpseo.framework.springutils.controller.CrudController;
import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.service.AuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController extends CrudController<Author, AuthorService,Object> {

    public AuthorController(AuthorService service) {
        super(service);
    }
}
