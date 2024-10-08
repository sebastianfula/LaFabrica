package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orden")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)
    private Person person;

    @Column(name = "fecha_orden", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "fecha_prometida_entrega")
    private LocalDateTime promisedDeliveryDate;

    @Column(name = "fecha_entrega")
    private LocalDateTime deliveryDate;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}