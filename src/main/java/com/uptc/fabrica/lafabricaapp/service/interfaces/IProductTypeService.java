package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.presentation.dto.ProductTypeDTO;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface IProductTypeService {
    CustomDetailMessage createProductType(ProductTypeDTO productTypeDTO);

    CustomDetailMessage getProductTypeById(Long id);

    CustomDetailMessage getAllProductTypes();

    CustomDetailMessage getAllProductTypesByMaterialId(Long materialId);

    CustomDetailMessage updateProductType(Long id, ProductTypeDTO productTypeUpdateDTO);

    CustomDetailMessage deleteProductType(Long id);
}
