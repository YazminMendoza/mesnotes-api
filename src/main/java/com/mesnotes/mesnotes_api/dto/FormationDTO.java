package com.mesnotes.mesnotes_api.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class FormationDTO {
    private UUID id;
    private String nom;
    private Double note;
    private List<PeriodeDTO> listePeriodes;
}
