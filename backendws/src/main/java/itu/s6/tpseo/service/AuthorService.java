package itu.s6.tpseo.service;

import itu.s6.tpseo.framework.springutils.service.CrudService;
import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.repository.AuthorRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends CrudService<Author, AuthorRepository> {
    public AuthorService(AuthorRepository repo, EntityManager manager) {
        super(repo, manager);
    }

    @Override
    public Class<Author> getEntityClass() {
        return Author.class;
    }
}
