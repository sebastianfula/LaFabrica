package com.uptc.fabrica.lafabricaapp.persistence.repository;

import com.uptc.fabrica.lafabricaapp.persistence.entity.WorkerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IWorkerSkillRepository extends JpaRepository<WorkerSkill, Long> {
    Optional<WorkerSkill> findByWorkerIdAndSkillId(Long workerId, Long skillId);
}
