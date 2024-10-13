package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.persistence.entity.OrderDetail;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Product;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IOrderDetailRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IOrderRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IProductRepository;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderDetailService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    @Transactional
    public CustomDetailMessage addNewDetails(Long orderId, List<OrderDetailDTO> orderDetailsDTO) {
        try {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Orden no encontrada con ID " + orderId));

            List<OrderDetail> savedDetails = new ArrayList<>();
            for (OrderDetailDTO detailDTO : orderDetailsDTO) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado con ID " + detailDTO.getProductId()));

                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProduct(product);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setUnitPrice(BigDecimal.valueOf(product.getProductPrice()));

                savedDetails.add(orderDetailRepository.save(detail));

            }

            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Detalles de la orden agregados correctamente",
                    savedDetails);

        } catch (Exception e) {
            log.error("Error al agregar los detalles de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudieron agregar los detalles de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getDetailsByOrderId(Long orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
            if (orderDetails.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron detalles para la orden con ID " + orderId,
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalles de la orden encontrados",
                    orderDetails);
        } catch (Exception e) {
            log.error("Error al obtener los detalles de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudieron obtener los detalles de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage updateADetail(Long orderId, Long detailId, OrderDetailDTO detailDTO) {
        try {
            OrderDetail existingDetail = orderDetailRepository.findByIdAndOrderId(detailId, orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de la orden no encontrado."));

            if (detailDTO.getProductId() != null) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado."));
                existingDetail.setProduct(product);

                existingDetail.setUnitPrice(BigDecimal.valueOf(product.getProductPrice()));
            }

            existingDetail.setQuantity(detailDTO.getQuantity());

            OrderDetail updatedDetail = orderDetailRepository.save(existingDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de la orden actualizado correctamente",
                    List.of(updatedDetail));

        } catch (Exception e) {
            log.error("Error al actualizar el detalle de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar el detalle de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage deleteADetail(Long orderId, Long detailId) {
        try {
            OrderDetail existingDetail = orderDetailRepository.findByIdAndOrderId(detailId, orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de la orden no encontrado."));

            orderDetailRepository.delete(existingDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de la orden eliminado correctamente",
                    Collections.emptyList());

        } catch (Exception e) {
            log.error("Error al eliminar el detalle de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar el detalle de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }
}
