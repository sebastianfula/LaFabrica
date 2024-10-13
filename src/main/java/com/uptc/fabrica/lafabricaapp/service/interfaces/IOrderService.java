package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDTO;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface IOrderService {

    CustomDetailMessage getOrderById(Long id);

    CustomDetailMessage getAllOrders();

    CustomDetailMessage createOrder(OrderDTO orderHeader);

    CustomDetailMessage updateOrder(Long id, OrderDTO orderHeader);

    CustomDetailMessage deleteOrder(Long id);
}