package itu.s6.tpseo.model;

import itu.s6.tpseo.model.parent.MyUserCredential;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@MappedSuperclass
@Getter
@Setter
public class Person extends MyUserCredential {
    private int permission;
}
