package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.persistence.entity.WorkerSkill;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IPersonRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.ISkillRepository;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IWorkerSkillRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IWorkerSkillService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkerSkillServiceImpl implements IWorkerSkillService {

    @Autowired
    private IPersonRepository workerRepository;

    @Autowired
    private ISkillRepository skillRepository;

    @Autowired
    private IWorkerSkillRepository workerSkillRepository;

    @Override
    @Transactional
    public CustomDetailMessage createWorkerSkill(Long workerId, List<Skill> skills) {
        try {
            Person worker = workerRepository.findById(workerId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Trabajador no encontrado."));

            if (!worker.getIsWorker()) {
                return new CustomDetailMessage(HttpStatus.BAD_REQUEST.value(),
                        "Error: La persona no es un trabajador.",
                        Collections.emptyList());
            }

            Set<Long> existingSkillIds = worker.getWorkerSkills().stream()
                    .map(ws -> ws.getSkill().getId())
                    .collect(Collectors.toSet());

            for (Skill skill : skills) {
                // Busca la habilidad en la base de datos
                Skill existingSkill = skillRepository.findById(skill.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Error: Habilidad no encontrada."));

                if (existingSkillIds.contains(existingSkill.getId())) {
                    log.warn("La habilidad con ID {} ya está asociada al trabajador {}", existingSkill.getId(), workerId);
                    return new CustomDetailMessage(HttpStatus.OK.value(),
                            "La habilidad  ya está asociada al trabajador .",
                            Collections.emptyList());
                }

                WorkerSkill workerSkill = new WorkerSkill();
                workerSkill.setWorker(worker);
                workerSkill.setSkill(existingSkill);
                worker.getWorkerSkills().add(workerSkill);
            }

            workerRepository.saveAndFlush(worker);
            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Habilidades del trabajador creadas correctamente.",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al crear habilidades para el trabajador: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudieron crear las habilidades del trabajador. " + e.getMessage(),
                    Collections.emptyList());
        }
    }


    @Override
    public CustomDetailMessage getWorkerSkillsByWorkerId(Long workerId) {
        try {
            Person worker = workerRepository.findById(workerId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Trabajador no encontrado."));

            List<Skill> skills = worker.getWorkerSkills().stream()
                    .map(WorkerSkill::getSkill)
                    .collect(Collectors.toList());

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Habilidades del trabajador obtenidas correctamente.",
                    skills);
        } catch (Exception e) {
            log.error("Error al obtener habilidades del trabajador: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudieron obtener las habilidades del trabajador. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage updateWorkerSkill(Long workerId, Long skillId, Skill skill) {
        try {
            Person worker = workerRepository.findById(workerId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Trabajador no encontrado."));

            WorkerSkill existingWorkerSkill = worker.getWorkerSkills().stream()
                    .filter(ws -> ws.getSkill().getId().equals(skillId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Error: Habilidad no encontrada para el trabajador."));

            existingWorkerSkill.getSkill().setSkillName(skill.getSkillName());
            skillRepository.save(existingWorkerSkill.getSkill());

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Habilidad del trabajador actualizada correctamente.",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al actualizar la habilidad del trabajador: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar la habilidad del trabajador. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    @Transactional
    public CustomDetailMessage deleteWorkerSkill(Long workerId, Long skillId) {
        try {
             workerRepository.findById(workerId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Trabajador no encontrado."));

            WorkerSkill existingWorkerSkill = workerSkillRepository.findByWorkerIdAndSkillId(workerId, skillId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Habilidad no encontrada para el trabajador."));
            workerSkillRepository.delete(existingWorkerSkill);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Habilidad del trabajador eliminada correctamente.",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al eliminar la habilidad del trabajador: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar la habilidad del trabajador. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

}
