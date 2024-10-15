package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.service.implementation.SkillServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CustomDetailMessage> getSkillById(@PathVariable Long id) {
        CustomDetailMessage response = skillService.getSkillById(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
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
    public ResponseEntity<CustomDetailMessage> deleteSkill(@PathVariable Long id) {
        CustomDetailMessage responseMessage = skillService.deleteSkill(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getCode()));
    }
}
