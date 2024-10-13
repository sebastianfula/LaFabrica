package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IPersonService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("persons")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage> createPerson(@RequestBody Person person) {
        CustomDetailMessage response = personService.createPerson(person);
        if (response.getCode() == HttpStatus.CREATED.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            log.error("Error al crear la persona: {}", response.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getPersonById(@PathVariable Long id) {
        CustomDetailMessage response = personService.getPersonById(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else if (response.getCode() == HttpStatus.NOT_FOUND.value()) {
            log.warn("Persona no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            log.error("Error al obtener la persona con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllPersons() {
        CustomDetailMessage response = personService.getAllPersons();
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else {
            log.error("Error al obtener todas las personas: {}", response.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        CustomDetailMessage response = personService.updatePerson(id, person);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else if (response.getCode() == HttpStatus.NOT_FOUND.value()) {
            log.warn("Persona no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            log.error("Error al actualizar la persona con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deletePerson(@PathVariable Long id) {
        CustomDetailMessage response = personService.deletePerson(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else if (response.getCode() == HttpStatus.NOT_FOUND.value()) {
            log.warn("Persona no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            log.error("Error al eliminar la persona con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<CustomDetailMessage> getAllClients() {
        CustomDetailMessage response = personService.getAllCustomers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/workers")
    public ResponseEntity<CustomDetailMessage> getAllWorkers() {
        CustomDetailMessage response = personService.getAllWorkers();
        return ResponseEntity.ok(response);
    }
}
