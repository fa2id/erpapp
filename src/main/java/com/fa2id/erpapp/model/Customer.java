package com.fa2id.erpapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "customer_first_name", nullable = false)
    private String customerFirstName;

    @Column(name = "customer_last_name", nullable = false)
    private String customerLastName;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String customerEmail;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderCustomer")
    private Set<Order> customerOrders;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Set<Order> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(Set<Order> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
