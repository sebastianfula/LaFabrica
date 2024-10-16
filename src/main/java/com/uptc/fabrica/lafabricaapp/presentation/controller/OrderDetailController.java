package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailDTO;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailRequestDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderDetailService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("OrderDetails")
@Tag(name = "Detalles de Órdenes", description = "API para gestionar detalles de órdenes")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService orderDetailService;

    @Operation(summary = "Añadir nuevos detalles a una orden", description = "Añade nuevos detalles a una orden específica.")
    @PostMapping("/{orderId}")
    public ResponseEntity<CustomDetailMessage> addNewDetails(
            @Parameter(description = "ID de la orden", required = true)
            @PathVariable Long orderId,
            @Parameter(description = "Objeto con los detalles de la orden a añadir", required = true)
            @RequestBody OrderDetailRequestDTO orderDetailsRequest) {
        CustomDetailMessage response = orderDetailService.addNewDetails(orderId, orderDetailsRequest.getDetails());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener detalles por ID de orden", description = "Obtiene los detalles de una orden específica por su ID.")
    @GetMapping("/{orderId}")
    public ResponseEntity<CustomDetailMessage> getDetailsByOrderId(
            @Parameter(description = "ID de la orden a buscar", required = true)
            @PathVariable Long orderId) {
        CustomDetailMessage response = orderDetailService.getDetailsByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar un detalle de orden", description = "Actualiza un detalle específico de una orden.")
    @PutMapping("/{orderId}/{detailId}")
    public ResponseEntity<CustomDetailMessage> updateADetail(
            @Parameter(description = "ID de la orden", required = true)
            @PathVariable Long orderId,
            @Parameter(description = "ID del detalle a actualizar", required = true)
            @PathVariable Long detailId,
            @Parameter(description = "Objeto con los datos del detalle actualizado", required = true)
            @RequestBody OrderDetailDTO detailDTO) {
        CustomDetailMessage response = orderDetailService.updateADetail(orderId, detailId, detailDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar un detalle de orden", description = "Elimina un detalle específico de una orden.")
    @DeleteMapping("/{orderId}/{detailId}")
    public ResponseEntity<CustomDetailMessage> deleteADetail(
            @Parameter(description = "ID de la orden", required = true)
            @PathVariable Long orderId,
            @Parameter(description = "ID del detalle a eliminar", required = true)
            @PathVariable Long detailId) {
        CustomDetailMessage response = orderDetailService.deleteADetail(orderId, detailId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
