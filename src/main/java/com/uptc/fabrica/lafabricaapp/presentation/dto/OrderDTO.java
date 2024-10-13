package com.uptc.fabrica.lafabricaapp.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long personId;
    @CurrentTimestamp
    private LocalDateTime orderDate;
    private LocalDateTime promisedDeliveryDate;
    private LocalDateTime deliveryDate;
    private List<OrderDetailDTO> orderDetails;
}
