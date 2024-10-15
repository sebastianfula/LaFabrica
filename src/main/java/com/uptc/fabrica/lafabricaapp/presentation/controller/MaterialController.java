package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.service.implementation.MaterialServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialServiceImpl materialService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage> createMaterial(@RequestBody Material materialRequest) {
        CustomDetailMessage responseMessage = materialService.createMaterial(materialRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getMaterialById(@PathVariable Long id) {
        CustomDetailMessage response = materialService.getMaterialById(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllMaterials() {
        CustomDetailMessage responseMessage = materialService.getAllMaterials();
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateMaterial(@PathVariable Long id, @RequestBody Material material) {
        CustomDetailMessage responseMessage = materialService.updateMaterial(id, material);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deleteSkill(@PathVariable Long id) {
        CustomDetailMessage responseMessage = materialService.deleteMaterial(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }
}