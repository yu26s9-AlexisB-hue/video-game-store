package org.yearup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository, ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Transactional
    public Order checkout(int userId){
       ShoppingCart cart = shoppingCartService.getByUserId(userId);
        Order order = new Order();
        order.setUserId(userId);
        order.setDate(LocalDate.now());
        order.setShippingAmount(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        for (ShoppingCartItem item : cart.getItems().values()){
            OrderLineItem lineItem = new OrderLineItem();

            lineItem.setOrderLineId(savedOrder.getOrderId());
            lineItem.setProductLineId(item.getProductId());
            lineItem.setQuantity(item.getQuantity());
            lineItem.setSalesPrice(BigDecimal.valueOf(item.getProduct().getPrice()));
            lineItem.setDiscount(BigDecimal.valueOf(item.getDiscountPercent()));
            orderLineItemRepository.save(lineItem);
        }
        shoppingCartService.clearCart(userId);

        return savedOrder;

    }
}
