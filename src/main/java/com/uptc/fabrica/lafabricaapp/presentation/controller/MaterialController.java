package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.service.implementation.MaterialServiceImpl;
import com.uptc.fabrica.lafabricaapp.service.implementation.SkillServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Optional<Material>> getMaterialById(@PathVariable Long id) {
        Optional<Material> material = materialService.getMaterialById(id);
        if (material.isPresent()) {
            return new ResponseEntity<>(material, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}