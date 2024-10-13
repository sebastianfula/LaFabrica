package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Order;
import com.uptc.fabrica.lafabricaapp.persistence.entity.OrderDetail;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Product;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IOrderDetailRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IOrderRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IPersonRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IProductRepository;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDTO;
import com.uptc.fabrica.lafabricaapp.presentation.dto.OrderDetailDTO;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOrderService;
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
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IPersonRepository personRepository;

    @Autowired
    private IProductRepository productRepository;

    @Override
    @Transactional
    public CustomDetailMessage createOrder(OrderDTO orderHeader) {
        try {
            Person person = personRepository.findById(orderHeader.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Error: La persona no existe."));

            Order order = new Order();
            order.setPerson(person);
            order.setOrderDate(orderHeader.getOrderDate());
            order.setPromisedDeliveryDate(orderHeader.getPromisedDeliveryDate());
            order.setDeliveryDate(orderHeader.getDeliveryDate());

            Order savedOrder = orderRepository.save(order);

            List<OrderDetail> savedDetails = new ArrayList<>();
            for (OrderDetailDTO detailDTO : orderHeader.getOrderDetails()) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado."));

                OrderDetail detail = new OrderDetail();
                detail.setOrder(savedOrder);
                detail.setProduct(product);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setUnitPrice(BigDecimal.valueOf(product.getProductPrice()));

                savedDetails.add(orderDetailRepository.save(detail));
            }

            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Orden creada correctamente",
                    List.of(savedOrder));

        } catch (Exception e) {
            log.error("Error al crear la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getOrderById(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Orden no encontrada con ID " + id));
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Orden encontrada",
                    List.of(order));
        } catch (Exception e) {
            log.error("Error al obtener la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            if (orders.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron órdenes.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Órdenes encontradas",
                    orders);
        } catch (Exception e) {
            log.error("Error al obtener todas las órdenes: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener las órdenes. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage updateOrder(Long id, OrderDTO orderHeader) {
        try {
            Order existingOrder = orderRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Orden no encontrada con ID " + id));

            Person person = personRepository.findById(orderHeader.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Error: La persona no existe."));
            existingOrder.setPerson(person);
            existingOrder.setOrderDate(orderHeader.getOrderDate());
            existingOrder.setPromisedDeliveryDate(orderHeader.getPromisedDeliveryDate());
            existingOrder.setDeliveryDate(orderHeader.getDeliveryDate());

            orderDetailRepository.deleteByOrderId(id);
            List<OrderDetail> updatedDetails = new ArrayList<>();
            for (OrderDetailDTO detailDTO : orderHeader.getOrderDetails()) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Producto no encontrado."));

                OrderDetail detail = new OrderDetail();
                detail.setOrder(existingOrder);
                detail.setProduct(product);
                detail.setQuantity(detailDTO.getQuantity());
                detail.setUnitPrice(BigDecimal.valueOf(product.getProductPrice()));

                updatedDetails.add(orderDetailRepository.save(detail));
            }
            Order updatedOrder = orderRepository.save(existingOrder);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Orden actualizada correctamente",
                    List.of(updatedOrder));
        } catch (Exception e) {
            log.error("Error al actualizar la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al actualizar la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage deleteOrder(Long id) {
        try {
            if (!orderRepository.existsById(id)) {
                log.warn("Orden no encontrada con ID: {}", id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Error: Orden no encontrada con ID " + id,
                        Collections.emptyList());
            }

            orderDetailRepository.deleteByOrderId(id);
            orderRepository.deleteById(id);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Orden eliminada correctamente",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al eliminar la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }
}