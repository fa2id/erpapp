package com.fa2id.erpapp.controller;

import com.fa2id.erpapp.model.Customer;
import com.fa2id.erpapp.model.Item;
import com.fa2id.erpapp.model.Order;
import com.fa2id.erpapp.model.User;
import com.fa2id.erpapp.service.CustomerService;
import com.fa2id.erpapp.service.ItemService;
import com.fa2id.erpapp.service.OrderService;
import com.fa2id.erpapp.service.UserService;
import com.fa2id.erpapp.utils.MyJsonProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/sales")
public class SalesController {

    private final ObjectMapper objectMapper;

    private final CustomerService customerService;

    private final OrderService orderService;

    private final UserService userService;

    private final ItemService itemService;

    private final MyJsonProtocol myJsonProtocol;

    @Autowired
    public SalesController(ObjectMapper objectMapper,
                           CustomerService customerService,
                           OrderService orderService,
                           UserService userService,
                           ItemService itemService,
                           MyJsonProtocol myJsonProtocol) {
        this.objectMapper = objectMapper;
        this.customerService = customerService;
        this.orderService = orderService;
        this.userService = userService;
        this.itemService = itemService;
        this.myJsonProtocol = myJsonProtocol;
    }


    @RequestMapping(value = {"/", "/panel"}, method = RequestMethod.GET)
    public ModelAndView getPanel() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sales-panel");
        return modelAndView;
    }


    @RequestMapping(
            value = "/orders/v1/place",
            method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode placeOrder(Customer customer, int itemId, int itemQuantity) {
        String message;
        int status = 200;
        ObjectNode resultNode = objectMapper.createObjectNode();
        Customer existedCustomer = customerService.saveOrGetCustomer(customer);
        Order order = new Order();
        order.setOrderCustomer(existedCustomer);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User seller = userService.findByUsername(authentication.getName());
        order.setOrderSeller(seller);
        Set<Item> items = new HashSet<>();
        Item item = itemService.getItem(itemId);
        if (item != null) {
            boolean isItemAvailable = itemService.isItemAvailable(itemId, itemQuantity);
            if (isItemAvailable) {
                Item decreasedQuantityItem = itemService.decreaseQuantity(itemId, itemQuantity);
                items.add(decreasedQuantityItem);
                order.setOrderItems(items);
                order.setOrderStatus("successful");
                order.setOrderItemQuantity(itemQuantity);
                order.setOrderTotalPrice(itemQuantity * decreasedQuantityItem.getItemPrice());
                Order placedOrder = orderService.saveOrder(order);
                resultNode.put("orderId", placedOrder.getOrderId());
                message = "Order placed.";
            } else {
                message = "Item quantity not available.";
            }
        } else {
            message = "Item not found.";
        }
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/orders/v1/get/all",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getAllOrders() {
        String message;
        int status = 200;
        List<Order> orders = orderService.getAllOrders();
        List<ObjectNode> orderNodes = new ArrayList<>();
        myJsonProtocol.makeOrderArray(orders, orderNodes);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(orderNodes);
        if (orders.isEmpty())
            message = "No orders found.";
        else
            message = "Got all orders.";
        return myJsonProtocol.createResponseArrayResult(status, message, arrayNode);
    }


    @RequestMapping(
            value = "/orders/v1/get",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getOrder(int orderId) {
        String message;
        int status;
        ObjectNode resultNode = objectMapper.createObjectNode();
        Order order = orderService.getOrder(orderId);
        if (order != null) {
            resultNode.put("orderId", order.getOrderId());
            resultNode.put("orderStatus", order.getOrderStatus());
            resultNode.put("customerEmail", order.getOrderCustomer().getCustomerEmail());
            resultNode.put("customerFirstName", order.getOrderCustomer().getCustomerFirstName());
            resultNode.put("customerLastName", order.getOrderCustomer().getCustomerLastName());
            resultNode.put("sellerUsername", order.getOrderSeller().getUsername());
            Item item = new Item();
            for (Item orderItem : order.getOrderItems()) {
                item = orderItem;
            }
            resultNode.put("itemId", item.getItemId());
            resultNode.put("itemName", item.getItemName());
            resultNode.put("itemQuantity", order.getOrderItemQuantity());
            resultNode.put("itemCategoryId", item.getItemCategory().getCategoryId());
            resultNode.put("itemCategoryName", item.getItemCategory().getCategoryName());
            resultNode.put("orderTotalPrice", order.getOrderTotalPrice());
            message = "Got order successfully.";
            status = 200;
        } else {
            message = "Order not found.";
            status = 200;
        }
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(value = "/orders/v1/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public ObjectNode cancelOrder(int orderId) {
        String message;
        int status;
        ObjectNode resultNode = objectMapper.createObjectNode();
        Order existedOrder = orderService.getOrder(orderId);
        if (existedOrder != null) {
            if (existedOrder.getOrderStatus().equals("successful")) {
                existedOrder.setOrderStatus("canceled");
                orderService.saveOrder(existedOrder);
                message = "Order canceled.";
                status = 200;
            } else {
                message = "Order was canceled.";
                status = 200;
            }
        } else {
            message = "Order not existed.";
            status = 200;
        }

        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }


    @RequestMapping(
            value = "/orders/v1/search",
            method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode searchItems(@RequestParam(required = false) String orderStatus,
                                  @RequestParam(required = false) Double orderTotalPrice,
                                  @RequestParam(required = false) Integer orderItemQuantity) {
        List<Order> orders;
        if (orderStatus.equals("")) orderStatus = null;
        if (orderStatus == null && orderTotalPrice == null && orderItemQuantity == null) {
            orders = orderService.getAllOrders();
        } else if (orderStatus == null || orderTotalPrice == null || orderItemQuantity == null) {
            if (orderStatus != null && orderTotalPrice != null)
                orders = orderService.searchOrdersByStatusPrice(orderStatus, orderTotalPrice);
            else if (orderStatus != null && orderItemQuantity != null)
                orders = orderService.searchOrdersByStatusQuantity(orderStatus, orderItemQuantity);
            else if (orderTotalPrice != null && orderItemQuantity != null)
                orders = orderService.searchOrdersByPriceQuantity(orderTotalPrice, orderItemQuantity);
            else if (orderStatus != null)
                orders = orderService.searchOrdersByStatus(orderStatus);
            else if (orderTotalPrice != null)
                orders = orderService.searchOrdersByPrice(orderTotalPrice);
            else
                orders = orderService.searchOrdersByQuantity(orderItemQuantity);
        } else
            orders = orderService.searchOrdersByStatusPriceQuantity(orderStatus, orderTotalPrice, orderItemQuantity);
        List<ObjectNode> orderNodes = new ArrayList<>();
        myJsonProtocol.makeOrderArray(orders, orderNodes);
        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.addAll(orderNodes);
        String message = "Orders got successfully.";
        int status = 200;
        return myJsonProtocol.createResponseArrayResult(status, message, arrayNode);
    }


    @RequestMapping(value = "/customers/v1/get", method = RequestMethod.GET)
    @ResponseBody
    public ObjectNode getCustomer(String customerEmail) {
        String message;
        int status;
        ObjectNode resultNode = objectMapper.createObjectNode();
        Customer existedCustomer = customerService.getCustomerByEmail(customerEmail);
        if (existedCustomer != null) {
            resultNode.put("customerExisted", true);
            resultNode.put("customerEmail", existedCustomer.getCustomerEmail());
            resultNode.put("customerFirstName", existedCustomer.getCustomerFirstName());
            resultNode.put("customerLastName", existedCustomer.getCustomerLastName());
            message = "Customer existed.";
            status = 200;
        } else {
            resultNode.put("customerExisted", false);
            message = "Customer not existed.";
            status = 200;
        }
        return myJsonProtocol.createResponseObjectResult(status, message, resultNode);
    }
}
