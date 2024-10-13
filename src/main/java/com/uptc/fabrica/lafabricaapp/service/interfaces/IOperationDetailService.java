package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Machine;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

import java.util.List;

public interface IOperationDetailService {

    CustomDetailMessage addNewOperationDetails(Long personId, Long machineId);

    CustomDetailMessage getOperationDetailByPerson(Long personId);

    CustomDetailMessage updateOperationDetail(Long machineId, Long personId);

    CustomDetailMessage deleteOperationDetail(Long personId, Long machineId);

}
