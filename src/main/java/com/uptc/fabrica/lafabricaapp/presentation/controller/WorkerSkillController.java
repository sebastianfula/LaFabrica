package com.uptc.fabrica.lafabricaapp.presentation.controller;


import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IWorkerSkillService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("WorkerSkills")
public class WorkerSkillController {

    @Autowired
    private IWorkerSkillService workerSkillService;

    @PostMapping("/{workerId}")
    public ResponseEntity<CustomDetailMessage> createWorkerSkill(
            @PathVariable Long workerId,
            @RequestBody List<Skill> skills) {
        CustomDetailMessage response = workerSkillService.createWorkerSkill(workerId, skills);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/{workerId}")
    public ResponseEntity<CustomDetailMessage> getWorkerSkillsByWorkerId(@PathVariable Long workerId) {
        CustomDetailMessage response = workerSkillService.getWorkerSkillsByWorkerId(workerId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{workerId}/skills/{skillId}")
    public ResponseEntity<CustomDetailMessage> updateWorkerSkill(
            @PathVariable Long workerId,
            @PathVariable Long skillId,
            @RequestBody Skill skill) {
        CustomDetailMessage response = workerSkillService.updateWorkerSkill(workerId, skillId, skill);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{workerId}/skills/{skillId}")
    public ResponseEntity<CustomDetailMessage> deleteWorkerSkill(
            @PathVariable Long workerId,
            @PathVariable Long skillId) {
        CustomDetailMessage response = workerSkillService.deleteWorkerSkill(workerId, skillId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }
}