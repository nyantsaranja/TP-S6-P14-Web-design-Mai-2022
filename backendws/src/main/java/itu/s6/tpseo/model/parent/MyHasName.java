package itu.s6.tpseo.model.parent;

import itu.s6.tpseo.framework.springutils.model.HasName;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
public class MyHasName extends HasName {
    public String buildSlug() {
        String slug= this.getName().toLowerCase().replace(" ", "-");
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
        slug+="-"+this.getId();
        return slug;
    }
}
