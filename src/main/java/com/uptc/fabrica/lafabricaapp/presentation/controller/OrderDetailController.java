package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailDTO;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailRequestDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderDetailService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("OrderDetails")
public class OrderDetailController {

    @Autowired
    private IOrderDetailService orderDetailService;

    @PostMapping("/{orderId}")
    public ResponseEntity<CustomDetailMessage> addNewDetails(@PathVariable Long orderId,
                                                             @RequestBody OrderDetailRequestDTO orderDetailsRequest) {
        CustomDetailMessage response = orderDetailService.addNewDetails(orderId, orderDetailsRequest.getDetails());
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CustomDetailMessage> getDetailsByOrderId(@PathVariable Long orderId) {
        CustomDetailMessage response = orderDetailService.getDetailsByOrderId(orderId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{orderId}/{detailId}")
    public ResponseEntity<CustomDetailMessage> updateADetail(@PathVariable Long orderId,
                                                             @PathVariable Long detailId,
                                                             @RequestBody OrderDetailDTO detailDTO) {
        CustomDetailMessage response = orderDetailService.updateADetail(orderId, detailId, detailDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{orderId}/{detailId}")
    public ResponseEntity<CustomDetailMessage> deleteADetail(@PathVariable Long orderId,
                                                             @PathVariable Long detailId) {
        CustomDetailMessage response = orderDetailService.deleteADetail(orderId, detailId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
