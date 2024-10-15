package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.service.implementation.MaterialServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
@Tag(name = "Materiales", description = "API para gestionar materiales")
public class MaterialController {

    @Autowired
    private MaterialServiceImpl materialService;

    @Operation(summary = "Crear un nuevo material", description = "Crea un nuevo material en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage> createMaterial(
            @Parameter(description = "Objeto material a crear", required = true)
            @RequestBody Material materialRequest) {
        CustomDetailMessage responseMessage = materialService.createMaterial(materialRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Obtener material por ID", description = "Obtiene un material específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getMaterialById(
            @Parameter(description = "ID del material a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = materialService.getMaterialById(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Operation(summary = "Obtener todos los materiales", description = "Obtiene una lista de todos los materiales.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllMaterials() {
        CustomDetailMessage responseMessage = materialService.getAllMaterials();
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Actualizar un material", description = "Actualiza la información de un material existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateMaterial(
            @Parameter(description = "ID del material a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto material actualizado", required = true)
            @RequestBody Material material) {
        CustomDetailMessage responseMessage = materialService.updateMaterial(id, material);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Eliminar un material", description = "Elimina un material del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaterial(
            @Parameter(description = "ID del material a eliminar", required = true)
            @PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
