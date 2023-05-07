package itu.s6.tpseo.model.parent;

import itu.s6.tpseo.framework.springutils.model.HasId;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class MyHasId extends HasId {
}
