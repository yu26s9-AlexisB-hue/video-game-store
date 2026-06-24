package org.yearup.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.*;
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
    private final ProfileService profileService;

    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository, ShoppingCartService shoppingCartService, ProfileService profileService) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileService = profileService;
    }

    @Transactional
    public Order checkout(int userId){

       ShoppingCart cart = shoppingCartService.getByUserId(userId);

       //check if the cart is empty.
       if (cart.getItems().isEmpty()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot checkout with an empty cart");
       }

        Profile profile = profileService.getProfileByUserId(userId);
        Order order = new Order();
        order.setUserId(userId);
        order.setDate(LocalDate.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
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
