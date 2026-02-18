
package com.mesnotes.mesnotes_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sujets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sujet extends Module{
    private Double poids;

    @ManyToOne
    @JoinColumn(name = "periode_id")
    private Periode periode;

    @OneToMany(mappedBy = "sujet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Critere> listeCriteres = new ArrayList<>();    
}

