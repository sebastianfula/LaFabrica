package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Product;
import com.uptc.fabrica.lafabricaapp.persistence.entity.ProductType;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IMachineRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IProductRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IProductTypeRepository;
import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IProductService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductTypeRepository productTypeRepository;

    @Autowired
    private IMachineRepository machineRepository;

    @Override
    @Transactional
    public CustomDetailMessage createProduct(ProductDTO productDTO) {
        try {
            ProductType productType = productTypeRepository.findById(productDTO.getProductTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Error: El tipo de producto no existe."));
            Machine machine = machineRepository.findById(productDTO.getMachineId())
                    .orElseThrow(() -> new IllegalArgumentException("Error: La máquina no existe."));
            Product product = new Product();
            product.setProductName(productDTO.getName());
            product.setProductDescription(productDTO.getDescription());
            product.setProductPrice(productDTO.getPrice());
            product.setProductType(productType);
            product.setMachine(machine);

            Product savedProduct = productRepository.save(product);
            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Producto creado correctamente",
                    List.of(savedProduct));
        } catch (Exception e) {
            log.error("Error al crear el producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear el producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getProductById(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado con ID " + id));
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Producto encontrado",
                    List.of(product));
        } catch (Exception e) {
            log.error("Error al obtener el producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener el producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron productos.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Productos encontrados",
                    products);
        } catch (Exception e) {
            log.error("Error al obtener todos los productos: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los productos. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllProductsByMachineId(Long machineId) {
        try {
            List<Product> products = productRepository.findByMachineId(machineId);
            if (products.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron productos para la máquina.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Productos encontrados para la máquina",
                    products);
        } catch (Exception e) {
            log.error("Error al obtener productos por máquina: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los productos por máquina. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllProductsByProductTypeId(Long productTypeId) {
        try {
            List<Product> products = productRepository.findByProductTypeId(productTypeId);
            if (products.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron productos para el tipo de producto.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Productos encontrados para el tipo de producto",
                    products);
        } catch (Exception e) {
            log.error("Error al obtener productos por tipo de producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los productos por tipo de producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage updateProduct(Long id, ProductDTO productUpdateDTO) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado con ID " + id));

            existingProduct.setProductName(productUpdateDTO.getName());
            existingProduct.setProductDescription(productUpdateDTO.getDescription());
            existingProduct.setProductPrice(productUpdateDTO.getPrice());

            if (productUpdateDTO.getProductTypeId() != null) {
                ProductType productType = productTypeRepository.findById(productUpdateDTO.getProductTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Tipo de producto no encontrado."));
                existingProduct.setProductType(productType);
            }

            if (productUpdateDTO.getMachineId() != null) {
                Machine machine = machineRepository.findById(productUpdateDTO.getMachineId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Máquina no encontrada."));
                existingProduct.setMachine(machine);
            }

            Product updatedProduct = productRepository.save(existingProduct);
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Producto actualizado correctamente",
                    List.of(updatedProduct));
        } catch (Exception e) {
            log.error("Error al actualizar el producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar el producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage deleteProduct(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                log.warn("Producto no encontrado con ID: {}", id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Error: Producto no encontrado con ID " + id,
                        Collections.emptyList());
            }
            productRepository.deleteById(id);
            log.info("Producto eliminado exitosamente: {}", id);
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Producto eliminado correctamente",
                    Collections.emptyList());
        } catch (DataIntegrityViolationException e) {
            log.error("Error al eliminar la persona con ID: " + id, e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al eliminar la persona con ID: " + id +
                            ". No se puede eliminar porque está siendo utilizada por otras entidades.",
                    new ArrayList<>());
        } catch (Exception e) {
            log.error("Error al eliminar el producto: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar el producto. " + e.getMessage(),
                    Collections.emptyList());
        }
    }
}