package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
@Tag(name = "Órdenes", description = "API para gestionar órdenes")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Operation(summary = "Crear una nueva orden", description = "Crea una nueva orden en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage<Order>> createOrder(
            @Parameter(description = "Objeto orden a crear", required = true)
            @RequestBody OrderDTO orderDTO) {
        CustomDetailMessage<Order> response = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener orden por ID", description = "Obtiene una orden específica por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> getOrderById(
            @Parameter(description = "ID de la orden a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage<Order> response = orderService.getOrderById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener todas las órdenes", description = "Obtiene una lista de todas las órdenes.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage<List<Order>>> getAllOrders() {
        CustomDetailMessage<List<Order>> response = orderService.getAllOrders();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar una orden", description = "Actualiza la información de una orden existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> updateOrder(
            @Parameter(description = "ID de la orden a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto orden actualizado", required = true)
            @RequestBody OrderDTO orderDTO) {
        CustomDetailMessage<Order> response = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar una orden", description = "Elimina una orden del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<Order>> deleteOrder(
            @Parameter(description = "ID de la orden a eliminar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage<Order> response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
