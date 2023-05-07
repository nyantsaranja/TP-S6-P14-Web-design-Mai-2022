package itu.s6.tpseo.model.filter;

import itu.s6.tpseo.model.Author;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ArticleFilter {
    private String ilike_title;
    private String ilike_subtitle;
    private Author author;

    private Date max_publicationDate;
    private Date min_publicationDate;
}
