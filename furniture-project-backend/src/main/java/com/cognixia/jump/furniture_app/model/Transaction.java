package com.cognixia.jump.furniture_app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = -7516093189L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_number")
    private Integer transactionNumber;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    // @ManyToOne
    // @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float cost;

    public Transaction() {
    }

    public Transaction(Integer transactionNumber, User userId, Integer productId, Integer quantity, Float cost) {
        this.transactionNumber = transactionNumber;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Integer getTransactionNumber() {
        return this.transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getCost() {
        return this.cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Transaction transactionNumber(Integer transactionNumber) {
        setTransactionNumber(transactionNumber);
        return this;
    }

    public Transaction userId(User userId) {
        setUserId(userId);
        return this;
    }

    public Transaction productId(Integer productId) {
        setProductId(productId);
        return this;
    }

    public Transaction quantity(Integer quantity) {
        setQuantity(quantity);
        return this;
    }

    public Transaction cost(Float cost) {
        setCost(cost);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return Objects.equals(transactionNumber, transaction.transactionNumber) && Objects.equals(userId, transaction.userId) && Objects.equals(productId, transaction.productId) && Objects.equals(quantity, transaction.quantity) && Objects.equals(cost, transaction.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionNumber, userId, productId, quantity, cost);
    }

    @Override
    public String toString() {
        return "{" +
            " transactionNumber='" + getTransactionNumber() + "'" +
            ", userId='" + getUserId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", cost='" + getCost() + "'" +
            "}";
    }

}
