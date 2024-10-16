package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.persistence.repository.ISkillRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.ISkillService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SkillServiceImpl implements ISkillService {

    @Autowired
    ISkillRepository repository;

    @Override
    public CustomDetailMessage createSkill(Skill skillRequest) {
            try {
                Skill skill = repository.saveAndFlush(skillRequest);
                ArrayList<Skill> skillList = new ArrayList<>();
                skillList.add(skill);
                return new CustomDetailMessage(HttpStatus.CREATED.value(),
                        "Habilidad insertada correctamente",
                        skillList);
            } catch (Exception e) {
                log.error("Error al guardar la habilidad.", e);
                return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error: No ha podido insertar la habilidad " + e.getMessage(),
                        new ArrayList<>());
            }
    }

    @Override
    public CustomDetailMessage getSkillById(Long id) {
        try {
            Skill skill = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("No se encontró la habilidad con ID: {}", id);
                        return new EntityNotFoundException("Habilidad no encontrada");
                    });
            return new CustomDetailMessage(HttpStatus.OK.value(), "Habilidad encontrada", List.of(skill));
        } catch (EntityNotFoundException e) {
            return new CustomDetailMessage(HttpStatus.OK.value(), "Habilidad no encontrada", Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al obtener la habilidad con ID {}: {}", id, e.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la habilidad", Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllSkills() {
        try {
            List<Skill> skills = repository.findAll();
            if (skills.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron habilidades.",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Lista de habilidades obtenida correctamente",
                    new ArrayList<>(skills));
        } catch (Exception e) {
            log.error("Error al obtener la lista de habilidades.", e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se ha podido obtener la lista de habilidades " + e.getMessage(),
                    new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage updateSkill(Long id, Skill skill) {
        if (repository.existsById(id)) {
            try {
                skill.setId(id); 
                Skill updatedSkill = repository.saveAndFlush(skill);
                ArrayList<Skill> skillList = new ArrayList<>();
                skillList.add(updatedSkill);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Habilidad actualizada correctamente",
                        skillList);
            } catch (Exception e) {
                log.error("Error al actualizar la habilidad con ID: " + id, e);
                return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error: No se pudo actualizar la habilidad " + e.getMessage(),
                        new ArrayList<>());
            }
        } else {
            return new CustomDetailMessage(HttpStatus.NOT_FOUND.value(),
                    "Error: La habilidad con ID " + id + " no existe",
                    new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage deleteSkill(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Habilidad eliminada exitosamente: {}", id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Habilidad con ID: " + id + " eliminada correctamente.",
                        new ArrayList<>());
            } else {
                log.warn("Error: La habilidad con ID " + id + " no existe.");
                return new CustomDetailMessage(HttpStatus.NOT_FOUND.value(),
                        "Error: La habilidad con ID " + id + " no existe.",
                        new ArrayList<>());
            }
        } catch (DataIntegrityViolationException e) {
            log.error("Error al eliminar la habilidad con ID: " + id, e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al eliminar la habilidad con ID: " + id +
                            ". No se puede eliminar porque está siendo utilizada por otras entidades.",
                    new ArrayList<>());
        } catch (Exception e) {
            log.error("Error al eliminar la habilidad con ID: " + id, e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al eliminar la habilidad con ID: ",
                    new ArrayList<>());
        }
    }
}
