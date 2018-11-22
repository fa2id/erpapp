package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.model.Category;
import com.fa2id.erpapp.model.Item;
import com.fa2id.erpapp.service.CategoryService;
import com.fa2id.erpapp.service.ItemService;
import com.fa2id.erpapp.utils.MyJsonProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/scm")
public class ScmController {

    private final ObjectMapper objectMapper;
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final MyJsonProtocol myJsonProtocol;


    @Autowired
    public ScmController(ObjectMapper objectMapper,
                         ItemService itemService,
                         CategoryService categoryService,
                         MyJsonProtocol myJsonProtocol) {
        this.objectMapper = objectMapper;
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.myJsonProtocol = myJsonProtocol;
    }


    @RequestMapping(value = {"/", "/panel"}, method = RequestMethod.GET)
    public ModelAndView getPanel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scm-panel");
        return modelAndView;
    }


    @RequestMapping(
            value = "/items/v1",
            method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode addItem(Item item, String categoryName) {
        Item addedItem = itemService.addItem(item, categoryName);
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("itemId", addedItem.getItemId());
        String message = "Items got successfully.";
        int status = 200;
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/items/v1/get/all",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getAllItems() {
        List<Item> items = itemService.getAllItems();
        List<ObjectNode> itemNodes = new ArrayList<>();
        myJsonProtocol.makeItemArray(items, itemNodes);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(itemNodes);
        String message = "Items got successfully.";
        int status = 200;
        return myJsonProtocol.createResponseArrayResult(status, message, arrayNode);
    }


    @RequestMapping(
            value = "/items/v1/get",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getItem(int itemId) {
        Item item = itemService.getItem(itemId);
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("itemId", item.getItemId());
        resultNode.put("itemName", item.getItemName());
        resultNode.put("itemQuantity", item.getItemQuantity());
        resultNode.put("itemPrice", item.getItemPrice());
        resultNode.put("itemCategory", item.getItemCategory().getCategoryName());
        String message = "Item got successfully.";
        int status = 200;
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/items/v1/remove",
            method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode removeItem(@RequestParam int itemId) {
        itemService.removeItem(itemId);
        ObjectNode resultNode = objectMapper.createObjectNode();
        String message = "Item removed successfully.";
        int status = 200;
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/items/v1/edit",
            method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ObjectNode editItem(Item item, String categoryName) {
        Item existedItem = itemService.getItem(item.getItemId());
        existedItem.setItemName(item.getItemName());
        existedItem.setItemQuantity(item.getItemQuantity());
        existedItem.setItemPrice(item.getItemPrice());
        Item editedItem = itemService.addItem(existedItem, categoryName);
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("itemId", editedItem.getItemId());
        String message = "Items edited.";
        int status = 200;
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/items/v1/search",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode searchItems(@RequestParam(required = false) String itemName,
                                  @RequestParam(required = false) Double itemPrice,
                                  @RequestParam(required = false) Integer itemQuantity) {
        List<Item> items;
        if (itemName.equals("")) itemName = null;
        if (itemName == null && itemPrice == null && itemQuantity == null)
            items = itemService.getAllItems();
        else if (itemName == null || itemPrice == null || itemQuantity == null) {
            if (itemName != null && itemPrice != null)
                items = itemService.searchItemsByNamePrice(itemName, itemPrice);
            else if (itemName != null && itemQuantity != null)
                items = itemService.searchItemsByNameQuantity(itemName, itemQuantity);
            else if (itemPrice != null && itemQuantity != null)
                items = itemService.searchItemsByPriceQuantity(itemPrice, itemQuantity);
            else if (itemName != null)
                items = itemService.searchItemsByName(itemName);
            else if (itemPrice != null)
                items = itemService.searchItemsByPrice(itemPrice);
            else
                items = itemService.searchItemsByQuantity(itemQuantity);
        } else
            items = itemService.searchItemsByNamePriceQuantity(itemName, itemPrice, itemQuantity);
        List<ObjectNode> itemNodes = new ArrayList<>();
        myJsonProtocol.makeItemArray(items, itemNodes);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(itemNodes);
        String message = "Items got successfully.";
        int status = 200;
        return myJsonProtocol.createResponseArrayResult(status, message, arrayNode);
    }


    @RequestMapping(value = "/categories/v1/get/all", method = RequestMethod.GET)
    @ResponseBody
    public ArrayNode getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<ObjectNode> categoryNodes = new ArrayList<>();
        for (Category category : categories) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("categoryName", category.getCategoryName());
            categoryNodes.add(objectNode);
        }
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(categoryNodes);
        return arrayNode;
    }
}
