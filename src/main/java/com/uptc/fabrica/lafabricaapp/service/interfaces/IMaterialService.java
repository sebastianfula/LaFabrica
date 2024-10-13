package com.uptc.fabrica.lafabricaapp.service.interfaces;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;

import java.util.Optional;

public interface IMaterialService {

    CustomDetailMessage createMaterial(Material material);

    Optional<Material> getMaterialById(Long id);

    CustomDetailMessage getAllMaterials();

    CustomDetailMessage updateMaterial(Long id, Material material);

    void deleteMaterial(Long id);
}
