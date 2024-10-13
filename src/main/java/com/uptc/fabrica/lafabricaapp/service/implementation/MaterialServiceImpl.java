package com.uptc.fabrica.lafabricaapp.service.implementation;

import com.uptc.fabrica.lafabricaapp.persistence.entity.Material;
import com.uptc.fabrica.lafabricaapp.persistence.repository.IMaterialRepository;
import com.uptc.fabrica.lafabricaapp.service.interfaces.IMaterialService;
import com.uptc.fabrica.lafabricaapp.utils.CustomDetailMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MaterialServiceImpl implements IMaterialService {

    @Autowired
    IMaterialRepository repository;

    @Override
    public CustomDetailMessage createMaterial(Material materialRequest) {
        try {
            Material material = repository.saveAndFlush(materialRequest);

            return new CustomDetailMessage(
                    HttpStatus.CREATED.value(),
                    "Material insertado correctamente",
                    List.of(material)
            );
        } catch (Exception e) {
            log.error("Error al guardar el material.", e);
            return new CustomDetailMessage(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: No se pudo insertar el material. " + e.getMessage(),
                    Collections.emptyList()
            );
        }
    }

    @Override
    public CustomDetailMessage getMaterialById(Long id) {
        try {
            Material material = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("No se encontró el material con ID: {}", id);
                        return new EntityNotFoundException("Material no encontrado");
                    });
            return new CustomDetailMessage(HttpStatus.OK.value(), "Material encontrado", List.of(material)); // Lista inmutable
        } catch (EntityNotFoundException e) {
            return new CustomDetailMessage(HttpStatus.OK.value(), "Material no encontrado", Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al obtener el material con ID {}: {}", id, e.getMessage());
            return new CustomDetailMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al obtener el material", Collections.emptyList());
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

                return new CustomDetailMessage(
                        HttpStatus.OK.value(),
                        "Material actualizado correctamente",
                        List.of(updatedMaterial)
                );
            } catch (Exception e) {
                log.error("Error al actualizar el material con ID: {}", id, e);
                return new CustomDetailMessage(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error: No se pudo actualizar el material. " + e.getMessage(),
                        Collections.emptyList()
                );
            }
        } else {
            log.warn("El material con ID: {} no existe", id);
            return new CustomDetailMessage(
                    HttpStatus.NOT_FOUND.value(),
                    "Error: El material con ID " + id + " no existe",
                    Collections.emptyList()
            );
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
