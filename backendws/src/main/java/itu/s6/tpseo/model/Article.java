package itu.s6.tpseo.model;

import itu.s6.tpseo.framework.springutils.model.HasId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@Entity

public class Article extends HasId {
    @Column(unique = true, nullable = false)
    private String title;
    private String subtitle;
    private String content;
    private String summary;
    private String image;
    private Date publicationDate;
    @ManyToOne
    private Author author;
    private transient String slug;

    public String getSlug() {
        if(this.slug == null) {
            this.slug = this.buildSlug();
        }
        return this.slug;
    }

    public String buildSlug() {
        String slug= this.getTitle().toLowerCase().replace(" ", "-");
        // replace character with accent with those without
        slug = slug.replace("é", "e");
        slug = slug.replace("è", "e");
        slug = slug.replace("ê", "e");
        slug = slug.replace("à", "a");
        slug = slug.replace("â", "a");
        slug = slug.replace("î", "i");
        slug = slug.replace("ï", "i");
        slug = slug.replace("ô", "o");
        slug = slug.replace("ö", "o");
        slug = slug.replace("ù", "u");
        slug = slug.replace("û", "u");
        slug = slug.replace("ü", "u");
        slug = slug.replace("ç", "c");
        slug = slug.replace("œ", "oe");
        slug = slug.replace("æ", "ae");
        slug=slug.replace("[^a-z0-9-]", "");
//        replace space with -
        slug=slug.replace(" ", "-");
        slug+="-"+this.getId();
        return slug;
    }
}
