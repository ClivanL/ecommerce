package com.example.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {
    @JsonProperty("rating")
    private float rating;

    public Review(Long rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public Review() {
    }
}
