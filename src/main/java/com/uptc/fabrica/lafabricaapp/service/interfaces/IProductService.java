package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductDTO;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface IProductService {
    CustomDetailMessage createProduct(ProductDTO productDTO);

    CustomDetailMessage getProductById(Long id);

    CustomDetailMessage getAllProducts();

    CustomDetailMessage getAllProductsByMachineId(Long machineId);

    CustomDetailMessage getAllProductsByProductTypeId(Long productTypeId);

    CustomDetailMessage updateProduct(Long id, ProductDTO ProductUpdateDTO);

    CustomDetailMessage deleteProduct(Long id);
}
