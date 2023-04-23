package com.example.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty("rating")
    private Long rating;

    public Review(Long rating) {
        this.rating = rating;
    }

    public Long getRating() {
        return rating;
    }

    public Review() {
    }
}
