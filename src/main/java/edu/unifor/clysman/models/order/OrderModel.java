package edu.unifor.clysman.models.order;

import edu.unifor.clysman.models.Model;
import edu.unifor.clysman.models.user.UserModel;

import java.sql.Timestamp;

public class OrderModel implements Model {
    private int id;
    private String description;
    private int userId;
    private OrderStatus status;
    private Timestamp createdAt;

    public OrderModel() {
    }

    public OrderModel(String description, int userId) {
        this.description = description;
        this.userId = userId;
        this.status = OrderStatus.PENDING;
    }

    public OrderModel(int id, String description, int userId, OrderStatus status, Timestamp createdAt) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static OrderModel builder() {
        return new OrderModel();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public OrderModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public OrderModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public OrderModel setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderModel setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public OrderModel setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
