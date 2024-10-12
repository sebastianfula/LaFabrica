package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_operacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_operacion")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_maquina", nullable = false)
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Person person;
}
