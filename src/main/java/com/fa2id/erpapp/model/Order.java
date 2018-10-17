package com.fa2id.erpapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_item_quantity")
    private int orderItemQuantity;

    @Column(name = "order_total_price")
    private double orderTotalPrice;

    @ManyToOne
    @JoinTable(name = "orders_customer",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private Customer orderCustomer;

    @ManyToOne
    @JoinTable(name = "orders_user",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User orderSeller;

    @ManyToMany
    @JoinTable(name = "orders_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> orderItems;


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(Customer orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public User getOrderSeller() {
        return orderSeller;
    }

    public void setOrderSeller(User orderSeller) {
        this.orderSeller = orderSeller;
    }

    public Set<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<Item> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(int orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
