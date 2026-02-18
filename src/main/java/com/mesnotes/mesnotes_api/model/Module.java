package com.mesnotes.mesnotes_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass // Pas de table pour cette entité mère, mais oui pour ses enfants : Critere, Sujet, 
// Periode et Formation 
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private String id;

    @Column(nullable = false)
    private String nom;

    private Double note = 0.0;
}
