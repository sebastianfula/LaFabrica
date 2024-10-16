package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOperationDetailService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("operation")
@Tag(name = "Detalles de Operación", description = "API para gestionar detalles de operación")
public class OperationDetailController {

    @Autowired
    private IOperationDetailService operationDetailService;

    @Operation(summary = "Obtener operación por ID de persona", description = "Obtiene el detalle de operación específico por ID de persona.")
    @GetMapping("/{personId}")
    public ResponseEntity<CustomDetailMessage<Order>> getOperationByIdPerson(
            @Parameter(description = "ID de la persona a buscar", required = true)
            @PathVariable Long personId) {
        CustomDetailMessage<Order> response = operationDetailService.getOperationDetailByPerson(personId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Crear un nuevo detalle de operación", description = "Crea un nuevo detalle de operación para una persona y una máquina.")
    @PostMapping("/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage> createOperationDetail(
            @Parameter(description = "ID de la persona", required = true)
            @PathVariable Long personId,
            @Parameter(description = "ID de la máquina", required = true)
            @PathVariable Long machineId) {
        CustomDetailMessage response = operationDetailService.addNewOperationDetails(personId, machineId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar un detalle de operación", description = "Actualiza el detalle de operación existente para una persona y una máquina.")
    @PutMapping("{operationId}/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage<Order>> updateOperationDetail(
            @Parameter(description = "ID de la operación", required = true)
            @PathVariable Long operationId,
            @Parameter(description = "ID de la persona", required = true)
            @PathVariable Long personId,
            @Parameter(description = "ID de la máquina", required = true)
            @PathVariable Long machineId) {
        CustomDetailMessage<Order> response = operationDetailService.updateOperationDetail(operationId, machineId, personId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar un detalle de operación", description = "Elimina el detalle de operación para una persona y una máquina.")
    @DeleteMapping("/{personId}/{machineId}")
    public ResponseEntity<CustomDetailMessage<Order>> deleteOperationDetail(
            @Parameter(description = "ID de la persona", required = true)
            @PathVariable Long personId,
            @Parameter(description = "ID de la máquina", required = true)
            @PathVariable Long machineId) {
        CustomDetailMessage<Order> response = operationDetailService.deleteOperationDetail(personId, machineId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
