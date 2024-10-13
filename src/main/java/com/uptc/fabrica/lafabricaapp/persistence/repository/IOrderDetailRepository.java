package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.OrderDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM OrderDetail od WHERE od.order.id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);
}
