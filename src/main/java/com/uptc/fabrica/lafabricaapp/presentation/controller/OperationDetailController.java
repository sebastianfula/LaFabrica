package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOperationDetailService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("operation")
public class OperationDetailController {

    @Autowired
    private IOperationDetailService operationDetailService;

    @GetMapping("/{personId}")
    public ResponseEntity<CustomDetailMessage<Order>> getOperationByIdPerson(@PathVariable Long personId) {
        CustomDetailMessage<Order> response = operationDetailService.getOperationDetailByPerson(personId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PostMapping("/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage> createOperationDetail(
            @PathVariable Long personId,
            @PathVariable Long machineId) {
        CustomDetailMessage response = operationDetailService.addNewOperationDetails(personId,machineId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("{operationId}/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage<Order>> updateOperationDetail(
            @PathVariable Long operationId,
            @PathVariable Long personId,
            @PathVariable Long machineId) {
        CustomDetailMessage<Order> response = operationDetailService.updateOperationDetail(operationId,machineId,personId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage<Order>> deleteOperationDetail(
            @PathVariable Long personId,
            @PathVariable Long machineId) {
        CustomDetailMessage<Order> response = operationDetailService.deleteOperationDetail(personId,machineId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

}
