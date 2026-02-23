package com.mesnotes.mesnotes_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class FormationDTO {
    private String id;
    private String nom;
    private Double note;
    private List<PeriodeDTO> listePeriodes;
}
