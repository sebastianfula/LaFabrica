package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage> createProduct(@RequestBody ProductDTO productDTO) {
        CustomDetailMessage response = productService.createProduct(productDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getProductById(@PathVariable Long id) {
        CustomDetailMessage response = productService.getProductById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllProducts() {
        CustomDetailMessage response = productService.getAllProducts();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<CustomDetailMessage> getAllProductsByMachineId(@PathVariable Long machineId) {
        CustomDetailMessage response = productService.getAllProductsByMachineId(machineId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/productType/{productTypeId}")
    public ResponseEntity<CustomDetailMessage> getAllProductsByProductTypeId(@PathVariable Long productTypeId) {
        CustomDetailMessage response = productService.getAllProductsByProductTypeId(productTypeId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productUpdateDTO) {
        CustomDetailMessage response = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deleteProduct(@PathVariable Long id) {
        CustomDetailMessage response = productService.deleteProduct(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
