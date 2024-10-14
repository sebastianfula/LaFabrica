package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IPersonRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IPersonService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    public IPersonRepository personRepository;

    @Override
    public CustomDetailMessage createPerson(Person person) {
        try {
            if (personRepository.existsByIdentification(person.getIdentification())) {
                log.warn("La persona con identificación {} ya existe.", person.getIdentification());
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Error: La persona con identificación " + person.getIdentification() + " ya existe.",
                        Collections.emptyList());
            }
            Person savedPerson = personRepository.saveAndFlush(person);
            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Persona creada correctamente.",
                    List.of(savedPerson));
        } catch (Exception e) {
            log.error("Error al crear la persona: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear la persona. " + e.getMessage(),
                    Collections.emptyList());
        }
    }


    @Override
    public CustomDetailMessage getPersonById(Long id) {
        try {
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("No se encontró la persona con ID: {}", id);
                        return new EntityNotFoundException("Persona no encontrada");
                    });
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Persona encontrada",
                    List.of(person));
        } catch (EntityNotFoundException e) {
            log.warn("Persona no encontrada con ID: {}", id);
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Error: Persona no encontrada",
                    Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al obtener la persona con ID: {}", id, e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener la persona. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllPersons() {
        try {
            List<Person> persons = personRepository.findAll();
            if (persons.isEmpty()) {
                log.warn("No se encontraron personas en la base de datos.");
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron personas",
                        Collections.emptyList());
            } else {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Personas encontradas",
                        persons);
            }
        } catch (Exception e) {
            log.error("Error al obtener todas las personas: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener las personas. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage updatePerson(Long id, Person person) {
        if (personRepository.existsById(id)) {
            try {
                person.setId(id);
                Person updatedPerson = personRepository.saveAndFlush(person);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Persona actualizada correctamente",
                        List.of(updatedPerson));
            } catch (Exception e) {
                log.error("Error al actualizar la persona con ID: {}", id, e);
                return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error al actualizar la persona. " + e.getMessage(),
                        Collections.emptyList());
            }
        } else {
            log.warn("No se encontró la persona con ID: {}", id);
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Error: Persona con ID " + id + " no existe",
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage deletePerson(Long id) {
        try {
            if (personRepository.existsById(id)) {
                personRepository.deleteById(id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Persona eliminada correctamente",
                        Collections.emptyList());
            } else {
                log.warn("No se encontró la persona con ID: {}", id);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Error: Persona con ID " + id + " no existe",
                        Collections.emptyList());
            }
        } catch (Exception e) {
            log.error("Error al eliminar la persona con ID: {}", id, e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al eliminar la persona. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllCustomers() {
        try {
            List<Person> clients = personRepository.findByIsWorker(false);
            if (clients.isEmpty()) {
                log.warn("No se encontraron clientes en la base de datos.");
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron clientes",
                        Collections.emptyList());
            } else {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Clientes encontrados",
                        clients);
            }
        } catch (Exception e) {
            log.error("Error al obtener los clientes: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los clientes. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getAllWorkers() {
        try {
            List<Person> workers = personRepository.findByIsWorker(true);
            if (workers.isEmpty()) {
                log.warn("No se encontraron trabajadores en la base de datos.");
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se encontraron trabajadores",
                        Collections.emptyList());
            } else {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Trabajadores encontrados",
                        workers);
            }
        } catch (Exception e) {
            log.error("Error al obtener los trabajadores: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener los trabajadores. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

}