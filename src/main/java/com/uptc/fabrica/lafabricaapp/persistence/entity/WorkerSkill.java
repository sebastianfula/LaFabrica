package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "habilidad_trabajador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habilidad_trabajador")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Person worker;

    @ManyToOne
    @JoinColumn(name = "id_habilidad", nullable = false)
    private Skill skill;
}
