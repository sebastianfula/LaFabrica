package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "maquina")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maquina")
    private Long id;

    @Column(name = "numero_serie", nullable = false, length = 35)
    private String serialNumber;

    @Column(name = "marca", nullable = false, length = 45)
    private String brand;

    @Column(name = "modelo", nullable = false, length = 15)
    private String model;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime purchaseDate;

    @OneToMany(mappedBy = "machine")
    private List<ProductType> productTypes;
}