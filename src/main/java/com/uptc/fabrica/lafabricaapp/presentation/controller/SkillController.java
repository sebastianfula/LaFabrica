package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.persistence.service.implementation.SkillServiceImpl;
import com.uptc.fabrica.lafabricaapp.persistence.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    SkillServiceImpl skillService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage> createSkill(@RequestBody Skill skillRequest) {
        CustomDetailMessage responseMessage = skillService.createSkill(skillRequest);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Optional<Skill> skill = skillService.getSkillById(id);
        return skill.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllSkills() {
        CustomDetailMessage responseMessage = skillService.getAllSkills();
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        CustomDetailMessage responseMessage = skillService.updateSkill(id, skill);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
