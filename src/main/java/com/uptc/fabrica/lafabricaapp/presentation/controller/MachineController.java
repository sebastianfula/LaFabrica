package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.service.implementation.MachineServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/machines")
@Tag(name = "Máquinas", description = "API para gestionar máquinas")
public class MachineController {

    @Autowired
    private MachineServiceImpl machineService;

    @Operation(summary = "Crear una nueva máquina", description = "Crea una nueva máquina en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage> createMachine(
            @Parameter(description = "Objeto máquina a crear", required = true)
            @RequestBody Machine machine) {
        CustomDetailMessage response = machineService.createMachine(machine);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener máquina por ID", description = "Obtiene una máquina específica por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getMachineById(
            @Parameter(description = "ID de la máquina a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = machineService.getMachineById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener todas las máquinas", description = "Obtiene una lista de todas las máquinas.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllMachines() {
        CustomDetailMessage response = machineService.getAllMachines();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar una máquina", description = "Actualiza la información de una máquina existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateMachine(
            @Parameter(description = "ID de la máquina a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto máquina actualizado", required = true)
            @RequestBody Machine machine) {
        CustomDetailMessage response = machineService.updateMachine(id, machine);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar una máquina", description = "Elimina una máquina del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(
            @Parameter(description = "ID de la máquina a eliminar", required = true)
            @PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }
}
