package com.example.main.Sess;

import javax.persistence.*;


@Entity
@Table(name="session", schema="public")
public class Sess {

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
    private String sessToken;

    public Sess(Long id, Long userId, String sessToken) {
        this.id = id;
        this.userId = userId;
        this.sessToken = sessToken;
    }

    public Sess(Long userId, String sessToken) {
        this.userId = userId;
        this.sessToken = sessToken;
    }

    public Sess() {
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

    public String getSessToken() {
        return sessToken;
    }

    public void setSessToken(String sessToken) {
        this.sessToken = sessToken;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", userId=" + userId +
                ", sessToken='" + sessToken + '\'' +
                '}';
    }
}
