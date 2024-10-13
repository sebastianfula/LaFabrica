package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.persistence.entity.ProductType;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IMaterialRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IProductTypeRepository;
import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductTypeDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductTypeService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ProductTypeServiceImpl implements IProductTypeService {

    @Autowired
    private IProductTypeRepository productTypeRepository;
    @Autowired
    private IMaterialRepository materialRepository;


    @Override
    @Transactional
    public CustomDetailMessage createProductType(ProductTypeDTO productTypeDTO) {
        try {
            Material material = materialRepository.findById(productTypeDTO.getMaterialId())
                    .orElseThrow(() -> {
                        return new IllegalArgumentException("Error: El material con ID " + productTypeDTO.getMaterialId() + " no existe.");
                    });

            ProductType productType = new ProductType();
            productType.setProductTypeName(productTypeDTO.getProductTypeName());
            productType.setMaterial(material);

            ProductType savedProductType = productTypeRepository.save(productType);
            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Tipo de producto creado correctamente",
                    List.of(savedProductType));
        } catch (Exception e) {
            log.error("Error al crear el tipo de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear el tipo de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getProductTypeById(Long id) {
        try {
            ProductType productType = productTypeRepository.findById(id)
                    .orElseThrow(() -> {
                        return new IllegalArgumentException("Error: Tipo de producto no encontrado con ID " + id);
                    });
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Tipo de producto encontrado",
                    List.of(productType));
        } catch (Exception e) {
            log.error("Error al obtener el tipo de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener el tipo de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllProductTypesByMaterialId(Long materialId) {
        try {
            List<ProductType> productTypes = productTypeRepository.findByMaterialId(materialId);
            if (productTypes.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron tipos de producto para el material.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Tipos de producto encontrados",
                    productTypes);
        } catch (Exception e) {
            log.error("Error al obtener los tipos de producto para el material: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los tipos de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage updateProductType(Long id, ProductTypeDTO productTypeUpdateDTO) {
        try {
            ProductType existingProductType = productTypeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Tipo de producto no encontrado con ID " + id));

            existingProductType.setProductTypeName(productTypeUpdateDTO.getProductTypeName());

            if (productTypeUpdateDTO.getMaterialId() != null) {
                Material material = materialRepository.findById(productTypeUpdateDTO.getMaterialId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: El material con ID " + productTypeUpdateDTO.getMaterialId() + " no existe."));
                existingProductType.setMaterial(material);
            }
            ProductType updatedProductType = productTypeRepository.save(existingProductType);
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Tipo de producto actualizado correctamente",
                    List.of(updatedProductType));
        } catch (Exception e) {
            log.error("Error al actualizar el tipo de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar el tipo de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage deleteProductType(Long id) {
        try {
            if (!productTypeRepository.existsById(id)) {
                log.warn("Tipo de producto no encontrado con ID: {}", id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Error: Tipo de producto no encontrado con ID " + id,
                        Collections.emptyList());
            }

            productTypeRepository.deleteById(id);
            return new CustomDetailMessage(HttpStatus.OK.value(),  // Cambia a OK
                    "Tipo de producto eliminado correctamente",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al eliminar el tipo de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar el tipo de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllProductTypes() {
        try {
            List<ProductType> productTypes = productTypeRepository.findAll();
            if (productTypes.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron tipos de producto.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Tipos de producto encontrados",
                    productTypes);
        } catch (Exception e) {
            log.error("Error al obtener todos los tipos de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los tipos de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }
}
