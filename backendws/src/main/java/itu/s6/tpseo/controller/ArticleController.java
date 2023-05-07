package itu.s6.tpseo.controller;

import itu.s6.tpseo.framework.springutils.controller.CrudController;
import itu.s6.tpseo.model.Article;
import itu.s6.tpseo.service.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController extends CrudController<Article, ArticleService,Object> {

    public ArticleController(ArticleService service) {
        super(service);
    }
}
