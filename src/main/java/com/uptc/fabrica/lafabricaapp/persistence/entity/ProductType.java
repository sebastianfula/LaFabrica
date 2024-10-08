package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tipo_producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @ManyToOne
    @JoinColumn(name = "id_maquina", nullable = false)
    private Machine machine;

    @Column(name = "nombre_tipo_producto", nullable = false, length = 45)
    private String productTypeName;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;
}