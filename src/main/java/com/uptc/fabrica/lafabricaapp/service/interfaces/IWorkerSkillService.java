package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

import java.util.List;

public interface IWorkerSkillService {
    CustomDetailMessage createWorkerSkill(Long workerId, List<Skill> skill);

    CustomDetailMessage getWorkerSkillsByWorkerId(Long workerId);

    CustomDetailMessage updateWorkerSkill(Long workerId,Long skillId, Skill skill);

    CustomDetailMessage deleteWorkerSkill(Long workerId,Long skillId);
}
