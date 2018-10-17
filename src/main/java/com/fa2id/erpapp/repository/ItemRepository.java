package com.fa2id.erpapp.repository;

import com.fa2id.erpapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findItemsByItemName(String itemName);

    List<Item> findItemsByItemPrice(double price);

    List<Item> findItemsByItemQuantity(int itemQuantity);

    List<Item> findItemsByItemNameAndItemPrice(String itemName, double itemPrice);

    List<Item> findItemsByItemNameAndItemQuantity(String itemName, int itemQuantity);

    List<Item> findItemsByItemPriceAndItemQuantity(double itemPrice, int itemQuantity);

    List<Item> findItemsByItemNameAndItemPriceAndItemQuantity(String itemName,
                                                              double itemPrice,
                                                              int itemQuantity);
}
