package com.shopcore.inventory_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug; // Aquí guardaremos el slug o id del producto para hacer match

    @Column(nullable = false)
    private Integer quantity; // Cantidad disponible en bodega
}