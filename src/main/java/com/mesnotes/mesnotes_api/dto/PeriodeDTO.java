package com.mesnotes.mesnotes_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class PeriodeDTO {
    private String id;
    private String nom;
    private Double note;
    private Double poids;
    private List<SujetDTO> listeSujets;
}

