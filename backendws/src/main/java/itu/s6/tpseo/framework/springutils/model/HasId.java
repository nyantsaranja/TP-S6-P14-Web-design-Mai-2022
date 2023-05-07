package itu.s6.tpseo.framework.springutils.model;

import itu.s6.tpseo.framework.pdfutils.PdfGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public class HasId extends PdfGenerator {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
