package com.uptc.fabrica.lafabricaapp.presentation.controller;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.service.implementation.MachineServiceImpl;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/machines")
public class MachineController {

    @Autowired
    private MachineServiceImpl machineService;

    @PostMapping
    public ResponseEntity<CustomDetailMessage> createMachine(@RequestBody Machine machine) {
        CustomDetailMessage response = machineService.createMachine(machine);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> getMachineById(@PathVariable Long id) {
        CustomDetailMessage response = machineService.getMachineById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping
    public ResponseEntity<CustomDetailMessage> getAllMachines() {
        CustomDetailMessage response = machineService.getAllMachines();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomDetailMessage> updateMachine(@PathVariable Long id, @RequestBody Machine machine) {
        CustomDetailMessage response = machineService.updateMachine(id, machine);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }
}
