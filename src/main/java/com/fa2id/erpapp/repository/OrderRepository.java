package com.fa2id.erpapp.repository;

import com.fa2id.erpapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findOrdersByOrderStatus(String orderStatus);

    List<Order> findOrdersByOrderItemQuantity(int orderItemQuantity);

    List<Order> findOrdersByOrderTotalPrice(double orderTotalPrice);

    List<Order> findOrdersByOrderStatusAndOrderItemQuantity(String orderStatus, int orderItemQuantity);

    List<Order> findOrdersByOrderStatusAndOrderTotalPrice(String orderStatus, double orderTotalPrice);

    List<Order> findOrdersByOrderTotalPriceAndOrderItemQuantity(double orderTotalPrice, int orderItemQuantity);

    List<Order> findOrdersByOrderStatusAndOrderTotalPriceAndOrderItemQuantity(String orderStatus,
                                                                              double orderTotalPrice,
                                                                              int orderItemQuantity);


}
