package itu.s6.tpseo.model.parent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import itu.s6.tpseo.framework.springutils.LoginEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
public class MyUserCredential extends MyHasId implements LoginEntity {
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private Date birthdate;

    public void setPassword(String password) {
        this.password = DigestUtils.sha1Hex(password);
    }

}
