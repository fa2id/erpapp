package com.fa2id.erpapp.service;

import com.fa2id.erpapp.model.Order;
import com.fa2id.erpapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order getOrder(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public List<Order> searchOrdersByStatusPrice(String orderStatus, Double orderTotalPrice) {
        return orderRepository.findOrdersByOrderStatusAndOrderTotalPrice(orderStatus, orderTotalPrice);
    }

    @Transactional
    public List<Order> searchOrdersByStatusQuantity(String orderStatus, Integer orderItemQuantity) {
        return orderRepository.findOrdersByOrderStatusAndOrderItemQuantity(orderStatus, orderItemQuantity);
    }

    @Transactional
    public List<Order> searchOrdersByPriceQuantity(Double orderTotalPrice, Integer orderItemQuantity) {
        return orderRepository.findOrdersByOrderTotalPriceAndOrderItemQuantity(orderTotalPrice, orderItemQuantity);
    }

    @Transactional
    public List<Order> searchOrdersByStatus(String orderStatus) {
        return orderRepository.findOrdersByOrderStatus(orderStatus);
    }

    @Transactional
    public List<Order> searchOrdersByPrice(Double orderTotalPrice) {
        return orderRepository.findOrdersByOrderTotalPrice(orderTotalPrice);
    }

    @Transactional
    public List<Order> searchOrdersByQuantity(Integer orderItemQuantity) {
        return orderRepository.findOrdersByOrderItemQuantity(orderItemQuantity);
    }

    @Transactional
    public List<Order> searchOrdersByStatusPriceQuantity(String orderStatus,
                                                         Double orderTotalPrice,
                                                         Integer orderItemQuantity) {
        return orderRepository.findOrdersByOrderStatusAndOrderTotalPriceAndOrderItemQuantity(
                orderStatus,
                orderTotalPrice,
                orderItemQuantity);
    }
}
