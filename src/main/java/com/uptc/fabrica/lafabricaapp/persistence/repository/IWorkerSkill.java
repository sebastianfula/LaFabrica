package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.WorkerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkerSkill extends JpaRepository<WorkerSkill, Long> {
}
