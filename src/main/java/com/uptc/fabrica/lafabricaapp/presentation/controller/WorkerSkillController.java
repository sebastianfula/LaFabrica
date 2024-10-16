package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IWorkerSkillService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("WorkerSkills")
@Tag(name = "Habilidades de Trabajadores", description = "API para gestionar habilidades de trabajadores")
public class WorkerSkillController {

    @Autowired
    private IWorkerSkillService workerSkillService;

    @Operation(summary = "Crear habilidades para un trabajador", description = "Añade nuevas habilidades a un trabajador específico.")
    @PostMapping("/{workerId}")
    public ResponseEntity<CustomDetailMessage> createWorkerSkill(
            @Parameter(description = "ID del trabajador", required = true)
            @PathVariable Long workerId,
            @Parameter(description = "Lista de habilidades a añadir", required = true)
            @RequestBody List<Skill> skills) {
        CustomDetailMessage response = workerSkillService.createWorkerSkill(workerId, skills);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Obtener habilidades de un trabajador", description = "Obtiene las habilidades de un trabajador específico por su ID.")
    @GetMapping("/{workerId}")
    public ResponseEntity<CustomDetailMessage> getWorkerSkillsByWorkerId(
            @Parameter(description = "ID del trabajador a buscar", required = true)
            @PathVariable Long workerId) {
        CustomDetailMessage response = workerSkillService.getWorkerSkillsByWorkerId(workerId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Actualizar una habilidad de un trabajador", description = "Actualiza una habilidad específica de un trabajador.")
    @PutMapping("/{workerId}/skills/{skillId}")
    public ResponseEntity<CustomDetailMessage> updateWorkerSkill(
            @Parameter(description = "ID del trabajador", required = true)
            @PathVariable Long workerId,
            @Parameter(description = "ID de la habilidad a actualizar", required = true)
            @PathVariable Long skillId,
            @Parameter(description = "Objeto habilidad actualizado", required = true)
            @RequestBody Skill skill) {
        CustomDetailMessage response = workerSkillService.updateWorkerSkill(workerId, skillId, skill);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @Operation(summary = "Eliminar una habilidad de un trabajador", description = "Elimina una habilidad específica de un trabajador.")
    @DeleteMapping("/{workerId}/skills/{skillId}")
    public ResponseEntity<CustomDetailMessage> deleteWorkerSkill(
            @Parameter(description = "ID del trabajador", required = true)
            @PathVariable Long workerId,
            @Parameter(description = "ID de la habilidad a eliminar", required = true)
            @PathVariable Long skillId) {
        CustomDetailMessage response = workerSkillService.deleteWorkerSkill(workerId, skillId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}
