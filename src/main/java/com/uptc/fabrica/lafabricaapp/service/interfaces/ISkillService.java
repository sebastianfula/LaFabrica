package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

import java.util.Optional;

public interface ISkillService {
    CustomDetailMessage createSkill(Skill skill);

    Optional<Skill> getSkillById(Long id);

    CustomDetailMessage getAllSkills();

    CustomDetailMessage updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);
}
