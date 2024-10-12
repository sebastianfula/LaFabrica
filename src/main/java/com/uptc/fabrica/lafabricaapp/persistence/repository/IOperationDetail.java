package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.OperationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOperationDetail extends JpaRepository<OperationDetail, Long> {
}
