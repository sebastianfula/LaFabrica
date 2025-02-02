package com.uptc.fabrica.lafabricaapp.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Long productId;
    private Integer quantity;
    @JsonIgnore
    private BigDecimal unitPrice;
}