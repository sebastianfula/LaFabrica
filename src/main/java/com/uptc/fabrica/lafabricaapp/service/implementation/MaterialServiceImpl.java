package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.persistence.entity.Skill;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IMaterialRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IMaterialService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MaterialServiceImpl implements IMaterialService {

    @Autowired
    IMaterialRepository repository;

    @Override
    public CustomDetailMessage createMaterial(Material materialRequest) {
        try {
            Material material = repository.saveAndFlush(materialRequest);
            ArrayList<Material> materialList = new ArrayList<>();
            materialList.add(material);
            return new CustomDetailMessage(HttpStatus.CREATED.value(),
                    "Material insertado correctamente",
                    materialList);
        } catch (Exception e) {
            log.error("Error al guardar el material.", e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No ha podido insertar la habilidad " + e.getMessage(),
                    new ArrayList<>());
        }
    }

    @Override
    public Optional<Material> getMaterialById(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            log.error("Error al consultar el material con ID: " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public CustomDetailMessage getAllMaterials() {
        try {
            List<Material> materials = repository.findAll();
            if(materials.isEmpty()){
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "No se han encontrado materiales",
                        new ArrayList<>());
            }
            return new CustomDetailMessage(HttpStatus.OK.value(),
                    "Lista de materiales obtenida correctamente",
                    new ArrayList<>(materials));
        } catch (Exception e) {
            log.error("Error al obtener la lista de materiales.", e);
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se ha podido obtener la lista de materiales " + e.getMessage(),
                    new ArrayList<>());
        }
    }

    @Override
    public CustomDetailMessage updateMaterial(Long id, Material material) {
        if (repository.existsById(id)) {
            try {
                material.setId(id);
                Material updatedMaterial = repository.saveAndFlush(material);
                ArrayList<Material> materialList = new ArrayList<>();
                materialList.add(updatedMaterial);
                return new CustomDetailMessage(HttpStatus.OK.value(),
                        "Material actualizado correctamente",
                        materialList);
            } catch (Exception e) {
                log.error("Error al actualizar el material con ID: " + id, e);
                return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error: No se pudo actualizar el material." + e.getMessage(),
                        new ArrayList<>());
            }
        } else {
            return new CustomDetailMessage(HttpStatus.NOT_FOUND.value(),
                    "Error: La habilidad con ID " + id + " no existe",
                    new ArrayList<>());
        }
    }

    @Override
    public void deleteMaterial(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Material con ID: " + id + " eliminado correctamente.");
            } else {
                log.warn("Error: El material con ID " + id + " no existe.");
            }
        } catch (Exception e) {
            log.error("Error al eliminar el material con ID: " + id, e);
        }
    }
}
