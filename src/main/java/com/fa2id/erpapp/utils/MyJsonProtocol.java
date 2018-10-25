package com.fa2id.erpapp.utils;

import com.fa2id.erpapp.model.Item;
import com.fa2id.erpapp.model.Order;
import com.fa2id.erpapp.model.Role;
import com.fa2id.erpapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyJsonProtocol {

    private final ObjectMapper objectMapper;

    @Autowired
    public MyJsonProtocol(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectNode createResponseObjectResult(int status, String message, ObjectNode resultNode) {
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("status", status);
        responseNode.put("message", message);
        responseNode.set("result", resultNode);
        return responseNode;
    }


    public ObjectNode createResponseArrayResult(int status, String message, ArrayNode resultArray) {
        ObjectNode responseNode = objectMapper.createObjectNode();
        responseNode.put("status", status);
        responseNode.put("message", message);
        responseNode.putArray("result").addAll(resultArray);
        return responseNode;
    }


    public void makeOrderArray(List<Order> orders, List<ObjectNode> orderNodes) {
        for (Order order : orders) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("orderId", order.getOrderId());
            objectNode.put("orderStatus", order.getOrderStatus());
            objectNode.put("customerEmail", order.getOrderCustomer().getCustomerEmail());
            objectNode.put("customerFirstName", order.getOrderCustomer().getCustomerFirstName());
            objectNode.put("customerLastName", order.getOrderCustomer().getCustomerLastName());
            objectNode.put("sellerUsername", order.getOrderSeller().getUsername());
            Item item = new Item();
            for (Item orderItem : order.getOrderItems()) item = orderItem;
            objectNode.put("itemId", item.getItemId());
            objectNode.put("itemName", item.getItemName());
            objectNode.put("itemQuantity", order.getOrderItemQuantity());
            objectNode.put("itemCategoryId", item.getItemCategory().getCategoryId());
            objectNode.put("itemCategoryName", item.getItemCategory().getCategoryName());
            objectNode.put("orderTotalPrice", order.getOrderTotalPrice());
            orderNodes.add(objectNode);
        }
    }


    public void makeUserArray(List<User> users, List<ObjectNode> userNodes) {
        for (User user : users) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("username", user.getUsername());
            for (Role role: user.getRoles())
                objectNode.put("role", role.getRole());
            userNodes.add(objectNode);
        }
    }


    public void makeItemArray(List<Item> items, List<ObjectNode> itemNodes) {
        for (Item item : items) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("itemId", item.getItemId());
            objectNode.put("itemName", item.getItemName());
            objectNode.put("itemQuantity", item.getItemQuantity());
            objectNode.put("itemPrice", item.getItemPrice());
            objectNode.put("itemCategory", item.getItemCategory().getCategoryName());
            itemNodes.add(objectNode);
        }
    }
}
