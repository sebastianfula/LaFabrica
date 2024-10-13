package com.uptc.fabrica.lafabricaapp.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_producto", nullable = false)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "id_maquina", nullable = false)
    private Machine machine;

    @Column(name = "nombre_producto", nullable = false, length = 85)
    private String productName;

    @Column(name = "descripcion_producto", nullable = false, length = 85)
    private String productDescription;

    @Column(name = "precio_producto")
    private Double productPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails;
}