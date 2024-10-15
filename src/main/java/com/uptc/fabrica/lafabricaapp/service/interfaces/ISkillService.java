package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

public interface ISkillService {
    CustomDetailMessage createSkill(Skill skill);

    CustomDetailMessage getSkillById(Long id);

    CustomDetailMessage getAllSkills();

    CustomDetailMessage updateSkill(Long id, Skill skill);

    CustomDetailMessage deleteSkill(Long id);
}
