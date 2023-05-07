package itu.s6.tpseo.controller;

import itu.s6.tpseo.framework.springutils.controller.LoginController;
import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.service.AuthorLoginService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorLoginController extends LoginController<Author, AuthorLoginService> {
    public AuthorLoginController(AuthorLoginService service) {
        super(service);
    }

    @Override
    public String getRequestHeaderKey() {
        return "Authorization";
    }
}
