package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.ProductType;
import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductTypeDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductTypeService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ProductTypes")
@Tag(name = "Tipos de Producto", description = "API para gestionar tipos de producto")
public class ProductTypeController {

    @Autowired
    private IProductTypeService productTypeService;

    @Operation(summary = "Crear un nuevo tipo de producto", description = "Crea un nuevo tipo de producto en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage<ProductType>> createProductType(
            @Parameter(description = "Objeto tipo de producto a crear", required = true)
            @RequestBody ProductTypeDTO productTypeDTO) {
        CustomDetailMessage<ProductType> response = productTypeService.createProductType(productTypeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener tipo de producto por ID", description = "Obtiene un tipo de producto específico por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<ProductType>> getProductTypeById(
            @Parameter(description = "ID del tipo de producto a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage<ProductType> response = productTypeService.getProductTypeById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener tipos de producto por ID de material", description = "Obtiene una lista de tipos de producto específicos por ID de material.")
    @GetMapping("/byMaterial/{materialId}")
    public ResponseEntity<CustomDetailMessage<ProductType>> getAllProductTypesByMaterialId(
            @Parameter(description = "ID del material", required = true)
            @PathVariable Long materialId) {
        CustomDetailMessage<ProductType> response = productTypeService.getAllProductTypesByMaterialId(materialId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener todos los tipos de producto", description = "Obtiene una lista de todos los tipos de producto.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage<ProductType>> getAllProductTypes() {
        CustomDetailMessage<ProductType> response = productTypeService.getAllProductTypes();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar un tipo de producto", description = "Actualiza la información de un tipo de producto existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateProductType(
            @Parameter(description = "ID del tipo de producto a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto tipo de producto actualizado", required = true)
            @RequestBody ProductTypeDTO productTypeUpdateDTO) {
        CustomDetailMessage response = productTypeService.updateProductType(id, productTypeUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar un tipo de producto", description = "Elimina un tipo de producto del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage<ProductType>> deleteProductType(
            @Parameter(description = "ID del tipo de producto a eliminar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage<ProductType> response = productTypeService.deleteProductType(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
