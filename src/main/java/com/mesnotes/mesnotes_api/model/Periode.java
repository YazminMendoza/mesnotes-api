package com.mesnotes.mesnotes_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "periodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Periode extends Module{
    private Double poids;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "periode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sujet> listeSujets = new ArrayList<>();
}

