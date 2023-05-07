package itu.s6.tpseo.model.token;

import itu.s6.tpseo.model.Author;
import itu.s6.tpseo.model.parent.MyHasId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Token extends MyHasId {
    @ManyToOne
    private Author author;
    private String tokenValue;
    private Timestamp expiration_date;

}
