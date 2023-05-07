package itu.s6.tpseo.service;

import itu.s6.tpseo.framework.springutils.service.CrudService;
import itu.s6.tpseo.model.Article;
import itu.s6.tpseo.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends CrudService<Article, ArticleRepository> {
    public ArticleService(ArticleRepository repo, EntityManager manager) {
        super(repo, manager);
    }

    @Override
    public Class<Article> getEntityClass() {
        return Article.class;
    }
}
