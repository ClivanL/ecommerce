package com.example.user.data;

public class Sess {
    private Long userId;
    private String sessToken;

    public Sess(Long userId, String sessToken) {
        this.userId = userId;
        this.sessToken = sessToken;
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
}
