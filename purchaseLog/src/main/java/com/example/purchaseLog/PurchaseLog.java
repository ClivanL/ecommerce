package com.example.purchaseLog;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="purchaselog", schema="public")
public class PurchaseLog extends BaseEntity{
    @Id
    @SequenceGenerator(
            name="purchaselog_sequence",
            sequenceName = "purchaselog_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="purchaselog_sequence"
    )
    @JsonProperty("id")
    private Long id;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("reviewedAt")
    private LocalDateTime reviewedAt;

    @JsonProperty("rating")
    private Long rating;

    @JsonProperty("comments")
    private String comments;

    @JsonProperty("isSent")
    private Boolean isSent=false;

    @JsonProperty("isReceived")
    private Boolean isReceived=false;

    public PurchaseLog(Long id, Long itemId, Long userId) {
        this.id = id;
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog(Long itemId, Long userId) {
        this.itemId = itemId;
        this.userId = userId;
    }

    public PurchaseLog(Long itemId, Long userId, int quantity, Long ownerId) {
        this.itemId = itemId;
        this.userId = userId;
        this.quantity = quantity;
        this.ownerId=ownerId;
    }

    public PurchaseLog(Long rating, String comments) {
        this.rating = rating;
        this.comments = comments;
    }

    public PurchaseLog(Long rating) {
        this.rating = rating;
    }

    public PurchaseLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSent() {
        return isSent;
    }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "PurchaseLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", ownerId=" + ownerId +
                ", reviewedAt=" + reviewedAt +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", isSent=" + isSent +
                ", isReceived=" + isReceived +
                '}';
    }
}
