package com.ws.apple.ayuep.model;

import com.google.gson.annotations.SerializedName;
import com.ws.apple.ayuep.entity.ProductDBModel;

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
    private String createdTime;

    @SerializedName("Customer")
    private CustomerModel customer;

    @SerializedName("Product")
    private ProductDBModel product;

    @SerializedName("OrderId")
    private String orderId;

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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ProductDBModel getProduct() {
        return product;
    }

    public void setProduct(ProductDBModel product) {
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
