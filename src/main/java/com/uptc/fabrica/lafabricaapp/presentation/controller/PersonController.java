package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IPersonService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("persons")
@Tag(name = "Personas", description = "API para gestionar personas")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @Operation(summary = "Crear una nueva persona", description = "Crea una nueva persona en el sistema.")
    @PostMapping
    public ResponseEntity<CustomDetailMessage> createPerson(
            @Parameter(description = "Objeto persona a crear", required = true)
            @RequestBody Person person) {
        CustomDetailMessage response = personService.createPerson(person);
        if (response.getCode() == HttpStatus.CREATED.value()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            log.error("Error al crear la persona: {}", response.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Obtener persona por ID", description = "Obtiene una persona específica por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getPersonById(
            @Parameter(description = "ID de la persona a buscar", required = true)
            @PathVariable Long id) {
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

    @Operation(summary = "Obtener todas las personas", description = "Obtiene una lista de todas las personas.")
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

    @Operation(summary = "Actualizar una persona", description = "Actualiza la información de una persona existente.")
    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updatePerson(
            @Parameter(description = "ID de la persona a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objeto persona actualizado", required = true)
            @RequestBody Person person) {
        CustomDetailMessage response = personService.updatePerson(id, person);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else if (response.getCode() == HttpStatus.OK.value()) {
            log.warn("Persona no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            log.error("Error al actualizar la persona con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Eliminar una persona", description = "Elimina una persona del sistema.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> deletePerson(
            @Parameter(description = "ID de la persona a eliminar", required = true)
            @PathVariable Long id) {
        CustomDetailMessage response = personService.deletePerson(id);
        if (response.getCode() == HttpStatus.OK.value()) {
            return ResponseEntity.ok(response);
        } else if (response.getCode() == HttpStatus.OK.value()) {
            log.warn("Persona no encontrada con ID: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            log.error("Error al eliminar la persona con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes.")
    @GetMapping("/customers")
    public ResponseEntity<CustomDetailMessage> getAllClients() {
        CustomDetailMessage response = personService.getAllCustomers();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todos los trabajadores", description = "Obtiene una lista de todos los trabajadores.")
    @GetMapping("/workers")
    public ResponseEntity<CustomDetailMessage> getAllWorkers() {
        CustomDetailMessage response = personService.getAllWorkers();
        return ResponseEntity.ok(response);
    }
}
