package itu.s6.tpseo.repository;

import itu.s6.tpseo.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.tokenValue = ?1 and t.expiration_date > CURRENT_TIMESTAMP")
    Token findByTokenValue(String token);
}
