package com.uptc.fabrica.lafabricaapp.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDTO {
    private Long materialId;
    private String productTypeName;
}