package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.*;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IOperationDetailRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IMachineService;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IOperationDetailService;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IPersonService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class OperationDetailServiceImpl implements IOperationDetailService {

    @Autowired
    private IOperationDetailRepository operationDetailRepository;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IMachineService machineService;

    @Override
    public CustomDetailMessage addNewOperationDetails(Long personId, Long machineId) {
        try {

            Person person = getValidPerson(personId);
            Machine machine = getValidMachine(machineId);

            if (operationDetailRepository.findByPersonIdAndMachineId(personId, machineId).isPresent()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        String.format("La persona %s ya es operaria de la máquina con número de serie %s",
                                person.getFullName(), machine.getSerialNumber()),
                        Collections.emptyList());
            }

            OperationDetail operationDetail = new OperationDetail();
            operationDetail.setPerson(person);
            operationDetail.setMachine(machine);
            operationDetailRepository.save(operationDetail);

            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Detalles de la operación agregados correctamente",
                    List.of(operationDetail));

        } catch (IllegalArgumentException ex) {
            log.warn("Error al agregar detalle de operación: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        } catch (Exception ex) {
            log.error("Error al crear el detalle de operación: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear el detalle de operación. " + ex.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getOperationDetailByPerson(Long personId) {
        try {
            Person person = getValidPerson(personId);
            List<OperationDetail> operationDetails = operationDetailRepository.findByPersonId(person.getId());

            if (operationDetails.isEmpty()) {
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "El trabajador no tiene máquinas asignadas para operar",
                        Collections.emptyList());
            }

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalles de operación retornados correctamente",
                    operationDetails);

        } catch (IllegalArgumentException ex) {
            log.warn("Error al buscar detalles de operación: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        } catch (Exception ex) {
            log.error("Error al buscar los detalles de operación: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudieron encontrar los detalles de operación. " + ex.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage updateOperationDetail(Long operationId, Long machineId, Long personId) {
        try {

            OperationDetail existingOperationDetail = operationDetailRepository.findById(operationId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de operación no encontrado."));

            Person person = getValidPerson(personId);
            Machine machine = getValidMachine(machineId);

            existingOperationDetail.setPerson(person);
            existingOperationDetail.setMachine(machine);
            OperationDetail updatedDetail = operationDetailRepository.save(existingOperationDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de la operación actualizado correctamente",
                    List.of(updatedDetail));

        } catch (IllegalArgumentException ex) {
            log.warn("Error al actualizar el detalle de operación: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());

        } catch (Exception ex) {
            log.error("Error al actualizar el detalle de operación: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar el detalle de operación. " + ex.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage deleteOperationDetail(Long personId, Long machineId) {
        try {
            OperationDetail existingOperationDetail = operationDetailRepository.findByPersonIdAndMachineId(personId, machineId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de operación no encontrado."));

            operationDetailRepository.delete(existingOperationDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de operación eliminado correctamente",
                    Collections.emptyList());

        } catch (IllegalArgumentException ex) {
            log.warn("Error al eliminar el detalle de operación: {}", ex.getMessage());
            return new CustomDetailMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());

        } catch (Exception ex) {
            log.error("Error al eliminar el detalle de operación: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar el detalle de operación. " + ex.getMessage(),
                    Collections.emptyList());
        }
    }

    private Person getValidPerson(Long personId) {
        CustomDetailMessage personMessage = (CustomDetailMessage) personService.getPersonById(personId);
        if (personMessage.getData().isEmpty()) {
            throw new IllegalArgumentException("Persona no encontrada");
        }
        Person person = (Person) personMessage.getData().get(0);
        if (!person.getIsWorker()) {
            throw new IllegalArgumentException("La persona no es un trabajador");
        }
        return person;
    }

    private Machine getValidMachine(Long machineId) {
        CustomDetailMessage machineMessage = (CustomDetailMessage) machineService.getMachineById(machineId);
        if (machineMessage.getData().isEmpty()) {
            throw new IllegalArgumentException("Máquina no encontrada");
        }
        return (Machine) machineMessage.getData().get(0);
    }
}

