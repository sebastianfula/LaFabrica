package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Person;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface IPersonService {
    CustomDetailMessage createPerson(Person person);

    CustomDetailMessage getPersonById(Long id);

    CustomDetailMessage getAllPersons();

    CustomDetailMessage getAllCustomers();

    CustomDetailMessage getAllWorkers();

    CustomDetailMessage updatePerson(Long id, Person person);

    CustomDetailMessage deletePerson(Long id);
}
