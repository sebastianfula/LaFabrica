package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
