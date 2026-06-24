package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yearup.models.Order;
import org.yearup.models.User;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userservice;

    public OrderController(OrderService orderService, UserService userservice) {
        this.orderService = orderService;
        this.userservice = userservice;
    }

    @PostMapping
    public ResponseEntity<Order> checkOut(Principal principal){
        if (principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = principal.getName();
        User user = userservice.getByUserName(username);
        Order order = orderService.checkout(user.getId());
        return ResponseEntity.ok(order);
    }

}
