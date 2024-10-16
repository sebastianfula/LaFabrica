package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage> createProduct(
            @Parameter(description = "Objeto producto a crear", required = true)
            @RequestBody ProductDTO productDTO) {
        CustomDetailMessage response = productService.createProduct(productDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getProductById(
            @Parameter(description = "ID del producto a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = productService.getProductById(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllProducts() {
        CustomDetailMessage response = productService.getAllProducts();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener productos por ID de máquina", description = "Obtiene una lista de productos específicos por ID de máquina.")
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<CustomDetailMessage> getAllProductsByMachineId(
            @Parameter(description = "ID de la máquina", required = true)
            @PathVariable Long machineId) {
        CustomDetailMessage response = productService.getAllProductsByMachineId(machineId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Obtener productos por ID de tipo de producto", description = "Obtiene una lista de productos específicos por ID de tipo de producto.")
    @GetMapping("/productType/{productTypeId}")
    public ResponseEntity<CustomDetailMessage> getAllProductsByProductTypeId(
            @Parameter(description = "ID del tipo de producto", required = true)
            @PathVariable Long productTypeId) {
        CustomDetailMessage response = productService.getAllProductsByProductTypeId(productTypeId);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Actualizar un producto", description = "Actualiza la información de un producto existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateProduct(
            @Parameter(description = "ID del producto a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto producto actualizado", required = true)
            @RequestBody ProductDTO productUpdateDTO) {
        CustomDetailMessage response = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deleteProduct(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = productService.deleteProduct(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
