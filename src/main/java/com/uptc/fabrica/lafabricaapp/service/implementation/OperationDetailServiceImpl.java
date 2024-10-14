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
    private PersonServiceImpl personService;

    @Autowired
    private MachineServiceImpl machineService;

    @Override
    public CustomDetailMessage addNewOperationDetails(Long personId, Long machineId) {
        try{

            Person person = (Person) personService.getPersonById(personId).getData().get(0);
            Machine machine = (Machine) machineService.getMachineById(machineId).getData().get(0);

            OperationDetail operationDetail = new OperationDetail();
            operationDetail.setPerson(person);
            operationDetail.setMachine(machine);

            operationDetailRepository.save(operationDetail);

            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Detalles de la orden agregados correctamente",
                    List.of(operationDetail));
        }catch (Exception ex){
            log.error("Error al crear el detalle de operacion: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo crear el detalle de operacion. " + ex.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage getOperationDetailByPerson(Long personId) {
        try{
            Person person = (Person) personService.getPersonById(personId).getData().get(0);
            if(!person.getIsWorker()){
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "La persona consultada no es un trabajador",
                        Collections.emptyList());
            }
            List<OperationDetail> listOperationDetail = operationDetailRepository.findByPersonId(person.getId());
            if(listOperationDetail.isEmpty()){
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Trabajador sin maquinas operadas ",
                        Collections.emptyList());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(), "Detalles de operacion retornados correctamente", listOperationDetail);

        }catch (Exception ex){
            log.error("Error al buscar la operacion: {}", ex.getMessage(), ex);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se puedieron encontrar los detalles de operacion. " + ex.getMessage(),
                    Collections.emptyList());
        }

    }

    @Override
    public CustomDetailMessage updateOperationDetail( Long machineId, Long personId) {
        try {
            OperationDetail existingOperationDetail = operationDetailRepository.findByPersonIdAndMachineId(personId, machineId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de operacion no encontrado."));

            Person person = (Person) personService.getPersonById(personId).getData().get(0);
            Machine machine = (Machine) machineService.getMachineById(machineId).getData().get(0);

            if(!person.getIsWorker()){
                new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se puede actualizar la operacion ya que la persona no es un trabajador",
                        Collections.emptyList());
            }

            existingOperationDetail.setPerson(person);
            existingOperationDetail.setMachine(machine);

            OperationDetail updatedDetail = operationDetailRepository.save(existingOperationDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de la orden actualizado correctamente",
                    List.of(updatedDetail));

        } catch (Exception e) {
            log.error("Error al actualizar el detalle de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo actualizar el detalle de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }

    @Override
    public CustomDetailMessage deleteOperationDetail(Long personId, Long machineId) {
        try {
            OperationDetail existingOperationDetail = operationDetailRepository.findByPersonIdAndMachineId(personId, machineId)
                    .orElseThrow(() -> new IllegalArgumentException("Error: Detalle de operacion no encontrado."));

            operationDetailRepository.delete(existingOperationDetail);

            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Detalle de operacion eliminado correctamente",
                    Collections.emptyList());

        } catch (Exception e) {
            log.error("Error al eliminar el detalle de la orden: {}", e.getMessage(), e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo eliminar el detalle de la orden. " + e.getMessage(),
                    Collections.emptyList());
        }
    }
}
