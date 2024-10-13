package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface IMachineService {
    CustomDetailMessage createMachine(Machine machine);

    CustomDetailMessage getMachineById(Long id);

    CustomDetailMessage getAllMachines();

    CustomDetailMessage updateMachine(Long id, Machine machine);

    void deleteMachine(Long id);
}
