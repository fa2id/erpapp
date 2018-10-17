package com.fa2id.erpapp.service;

import com.fa2id.erpapp.model.Category;
import com.fa2id.erpapp.model.Item;
import com.fa2id.erpapp.repository.CategoryRepository;
import com.fa2id.erpapp.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public Item addItem(Item item, String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        item.setItemCategory(category);
        return itemRepository.save(item);
    }

    @Transactional
    public List<Item> searchItemsByNamePriceQuantity(String itemName, double itemPrice, int itemQuantity) {
        return itemRepository.findItemsByItemNameAndItemPriceAndItemQuantity(itemName, itemPrice, itemQuantity);
    }

    @Transactional
    public List<Item> searchItemsByNamePrice(String itemName, double itemPrice) {
        return itemRepository.findItemsByItemNameAndItemPrice(itemName, itemPrice);
    }

    @Transactional
    public List<Item> searchItemsByNameQuantity(String itemName, int itemQuantity) {
        return itemRepository.findItemsByItemNameAndItemQuantity(itemName, itemQuantity);
    }

    @Transactional
    public List<Item> searchItemsByPriceQuantity(double itemPrice, int itemQuantity) {
        return itemRepository.findItemsByItemPriceAndItemQuantity(itemPrice, itemQuantity);
    }

    @Transactional
    public List<Item> searchItemsByName(String itemName) {
        return itemRepository.findItemsByItemName(itemName);
    }

    @Transactional
    public List<Item> searchItemsByPrice(double itemPrice) {
        return itemRepository.findItemsByItemPrice(itemPrice);
    }

    @Transactional
    public List<Item> searchItemsByQuantity(int itemQuantity) {
        return itemRepository.findItemsByItemQuantity(itemQuantity);
    }

    @Transactional
    public Item getItem(int itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    @Transactional
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public void removeItem(int itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public boolean isItemAvailable(int itemId, int itemQuantity) {
        Item item = getItem(itemId);
        if (item == null)
            return false;
        return item.getItemQuantity() >= itemQuantity;
    }

    @Transactional
    public Item decreaseQuantity(int itemId, int itemQuantity) {
        Item item = getItem(itemId);
        if (item != null) {
            item.setItemQuantity(item.getItemQuantity() - itemQuantity);
            return itemRepository.save(item);
        } else return null;
    }
}
