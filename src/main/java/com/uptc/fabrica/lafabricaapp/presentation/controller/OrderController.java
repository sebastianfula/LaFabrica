package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage<Order>> createOrder(@RequestBody OrderDTO orderDTO) {
        CustomDetailMessage<Order> response = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> getOrderById(@PathVariable Long id) {
        CustomDetailMessage<Order> response = orderService.getOrderById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage<List<Order>>> getAllOrders() {
        CustomDetailMessage<List<Order>> response = orderService.getAllOrders();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> updateOrder(
            @PathVariable Long id,
            @RequestBody OrderDTO orderDTO) {
        CustomDetailMessage<Order> response = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> deleteOrder(@PathVariable Long id) {
        CustomDetailMessage<Order> response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
