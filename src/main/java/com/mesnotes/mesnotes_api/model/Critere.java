package com.mesnotes.mesnotes_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "criteres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Critere extends Module{
    private Double poids;

    @ManyToOne
    @JoinColumn(name = "sujet_id")
    private Sujet sujet;    
}
