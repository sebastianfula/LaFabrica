package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailDTO;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

import java.util.List;

public interface IOrderDetailService {
    CustomDetailMessage addNewDetails(Long orderId, List<OrderDetailDTO> orderDetailsDTO);

    CustomDetailMessage getDetailsByOrderId(Long orderId);

    CustomDetailMessage updateADetail(Long orderId, Long detailId, OrderDetailDTO detailDTO);

    CustomDetailMessage deleteADetail(Long orderId,Long detailId);
}
