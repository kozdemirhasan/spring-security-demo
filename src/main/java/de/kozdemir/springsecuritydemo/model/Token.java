package de.kozdemir.springsecuritydemo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Token {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    // Eager = Der User-Datensatz ist an jeden Token angebunden und muss nicht aus der DB exta abgefragt werden
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public Token() {
    }

    public Token(User user, TokenType type) {
        this.type = type;
        this.user = user;
        createdAt = LocalDateTime.now();
    }

    public enum TokenType {
        ACTIVATION, PASSWORD;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
