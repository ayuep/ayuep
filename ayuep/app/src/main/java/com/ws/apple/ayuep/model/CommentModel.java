package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by apple on 16/7/9.
 */

public class CommentModel {

    @SerializedName("CommentId")
    private String commentId;

    @SerializedName("Score")
    private int score;

    @SerializedName("Comment")
    private String comment;

    @SerializedName("CreatedTime")
    private Date createdTime;

    @SerializedName("Customer")
    private CustomerModel customer;

    @SerializedName("Product")
    private ProductModel product;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }
}
