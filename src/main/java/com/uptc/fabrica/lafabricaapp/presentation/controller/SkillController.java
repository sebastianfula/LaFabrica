package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.service.implementation.SkillServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
@Tag(name = "Habilidades", description = "API para gestionar habilidades")
public class SkillController {

    @Autowired
    SkillServiceImpl skillService;

    @Operation(summary = "Crear una nueva habilidad", description = "Crea una nueva habilidad en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage> createSkill(
            @Parameter(description = "Objeto habilidad a crear", required = true)
            @RequestBody Skill skillRequest) {
        CustomDetailMessage responseMessage = skillService.createSkill(skillRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Obtener habilidad por ID", description = "Obtiene una habilidad específica por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getSkillById(
            @Parameter(description = "ID de la habilidad a buscar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = skillService.getSkillById(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @Operation(summary = "Obtener todas las habilidades", description = "Obtiene una lista de todas las habilidades.")
    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllSkills() {
        CustomDetailMessage responseMessage = skillService.getAllSkills();
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Actualizar una habilidad", description = "Actualiza la información de una habilidad existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateSkill(
            @Parameter(description = "ID de la habilidad a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto habilidad actualizado", required = true)
            @RequestBody Skill skill) {
        CustomDetailMessage responseMessage = skillService.updateSkill(id, skill);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @Operation(summary = "Eliminar una habilidad", description = "Elimina una habilidad del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deleteSkill(
            @Parameter(description = "ID de la habilidad a eliminar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage responseMessage = skillService.deleteSkill(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }
}
