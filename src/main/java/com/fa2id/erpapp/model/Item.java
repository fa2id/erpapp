package com.fa2id.erpapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private int itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_price", nullable = false)
    private double itemPrice;

    @Column(name = "item_quantity", nullable = false)
    private int itemQuantity;

    @ManyToOne
    @JoinTable(name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonBackReference
    private Category itemCategory;

    @ManyToMany(mappedBy = "orderItems", fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    private Set<Order> itemOrders;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Set<Order> getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(Set<Order> itemOrders) {
        this.itemOrders = itemOrders;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}
