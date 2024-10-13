package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IMachineRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IMachineService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MachineServiceImpl implements IMachineService {

    @Autowired
    IMachineRepository machineRepository;

    @Override
    public CustomDetailMessage createMachine(Machine machine) {
        try {
            Machine savedMachine = machineRepository.saveAndFlush(machine);
            log.info("Máquina creada exitosamente: {}", savedMachine.getId());
            return new CustomDetailMessage(HttpStatus.CREATED.value(), "Máquina creada exitosamente", new ArrayList<>(List.of(savedMachine)));
        } catch (Exception e) {
            log.error("Error al crear la máquina: {}", e.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear la máquina", new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage getMachineById(Long id) {
        try {
            Machine machine = machineRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("No se encontró la máquina con ID: {}", id);
                        return new EntityNotFoundException("Máquina no encontrada");
                    });
            return new CustomDetailMessage(HttpStatus.OK.value(), "Máquina encontrada", new ArrayList<>(List.of(machine)));
        } catch (EntityNotFoundException e) {
            return new CustomDetailMessage(HttpStatus.OK.value(), "Máquina no encontrada", new ArrayList<>());
        } catch (Exception e) {
            log.error("Error al obtener la máquina con ID {}: {}", id, e.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener la máquina", new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage getAllMachines() {
        try {
            List<Machine> machines = machineRepository.findAll();
            if (machines.isEmpty()) {
                log.warn("No se encontraron máquinas en la base de datos.");
                return new CustomDetailMessage(HttpStatus.OK.value(), "No se encontraron máquinas", new ArrayList<>());
            } else {
                log.info("Máquinas recuperadas exitosamente, total: {}", machines.size());
                return new CustomDetailMessage(HttpStatus.OK.value(), "Máquinas recuperadas exitosamente", new ArrayList<>(machines));
            }
        } catch (Exception e) {
            log.error("Error al recuperar las máquinas: {}", e.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al recuperar las máquinas", new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage updateMachine(Long id, Machine machine) {
        return machineRepository.findById(id).map(existingMachine -> {
            try {
                existingMachine.setSerialNumber(machine.getSerialNumber());
                existingMachine.setBrand(machine.getBrand());
                existingMachine.setModel(machine.getModel());
                existingMachine.setPurchaseDate(machine.getPurchaseDate());
                Machine updatedMachine = machineRepository.saveAndFlush(existingMachine);
                log.info("Máquina actualizada exitosamente: {}", updatedMachine.getId());
                return new CustomDetailMessage(HttpStatus.OK.value(), "Máquina actualizada exitosamente", new ArrayList<>(List.of(updatedMachine)));
            } catch (Exception e) {
                log.error("Error al actualizar la máquina con ID {}: {}", id, e.getMessage());
                return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la máquina", new ArrayList<>());
            }
        }).orElseGet(() -> {
            log.error("No se encontró la máquina con ID: {}", id);
            return new CustomDetailMessage(HttpStatus.NOT_FOUND.value(), "Máquina no encontrada", new ArrayList<>());
        });
    }

    @Override
    public void deleteMachine(Long id) {
        try {
            if (machineRepository.existsById(id)) {
                machineRepository.deleteById(id);
                log.info("Máquina eliminada exitosamente: {}", id);
            } else {
                log.error("No se encontró la máquina con ID: {}", id);
                throw new EntityNotFoundException("Máquina no encontrada");
            }
        } catch (Exception e) {
            log.error("Error al eliminar la máquina con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar la máquina");
        }
    }
}
