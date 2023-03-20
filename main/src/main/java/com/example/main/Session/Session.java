package com.example.main.Session;

import javax.persistence.*;


@Entity
@Table(name="session", schema="public")
public class Session {

    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private Long userId;
    private String sessionToken;

    public Session(Long id, Long userId, String sessionToken) {
        this.id = id;
        this.userId = userId;
        this.sessionToken = sessionToken;
    }

    public Session(Long userId, String sessionToken) {
        this.userId = userId;
        this.sessionToken = sessionToken;
    }

    public Session() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
