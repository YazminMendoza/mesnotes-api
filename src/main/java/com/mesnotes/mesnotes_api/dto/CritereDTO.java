package com.mesnotes.mesnotes_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CritereDTO {
    private UUID id;
    private String nom;
    private Double note;
    private Double poids;
}
