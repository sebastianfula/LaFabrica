package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.OperationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOperationDetailRepository extends JpaRepository<OperationDetail, Long> {

    List<OperationDetail> findByPersonId (long personId);
    Optional<OperationDetail> findByPersonIdAndMachineId (long personId, long machineId);
}
