package com.uptc.fabrica.lafabricaapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    @Column(name = "identificacion", nullable = false, unique = true, length = 35)
    private String identification;

    @Column(name = "nombre_completo", nullable = false, length = 85)
    private String fullName;

    @Column(name = "direccion", nullable = false, length = 55)
    private String address;

    @Column(name = "fecha_nacimiento")
    private LocalDateTime birthDate;

    @Column(name = "nombre_contacto", length = 45)
    private String contactName;

    @Column(name = "numero_telefono", length = 15)
    private String phoneNumber;

    @Column(name = "es_trabajador", nullable = false)
    private Boolean isWorker;

    @OneToMany(mappedBy = "person")
    private List<Order> orders;
}