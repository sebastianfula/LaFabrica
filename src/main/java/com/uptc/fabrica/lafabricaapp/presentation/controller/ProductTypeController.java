package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.ProductType;
import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductTypeDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductTypeService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ProductTypes")
public class ProductTypeController {

    @Autowired
    private IProductTypeService productTypeService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage<ProductType>> createProductType(@RequestBody ProductTypeDTO productTypeDTO) {
        CustomDetailMessage<ProductType> response = productTypeService.createProductType(productTypeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<ProductType>> getProductTypeById(@PathVariable Long id) {
        CustomDetailMessage<ProductType> response = productTypeService.getProductTypeById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/byMaterial/{materialId}")
    public ResponseEntity<CustomDetailMessage<ProductType>> getAllProductTypesByMaterialId(@PathVariable Long materialId) {
        CustomDetailMessage<ProductType> response = productTypeService.getAllProductTypesByMaterialId(materialId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage<ProductType>> getAllProductTypes() {
        CustomDetailMessage<ProductType> response = productTypeService.getAllProductTypes();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateProductType(@PathVariable Long id,
                                                                 @RequestBody ProductTypeDTO productTypeUpdateDTO) {
        CustomDetailMessage response = productTypeService.updateProductType(id, productTypeUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<ProductType>> deleteProductType(@PathVariable Long id) {
        CustomDetailMessage<ProductType> response = productTypeService.deleteProductType(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}