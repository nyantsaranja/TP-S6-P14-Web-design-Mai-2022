package itu.s6.tpseo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import itu.s6.tpseo.model.token.Token;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Author extends Person{
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Article> articles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokens;
    public void setPermission(int permission) {
        super.setPermission(0);
    }
}
