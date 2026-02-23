package com.mesnotes.mesnotes_api.service;

import com.mesnotes.mesnotes_api.model.Sujet;
import com.mesnotes.mesnotes_api.dto.CritereDTO;
import com.mesnotes.mesnotes_api.dto.PeriodeDTO;
import com.mesnotes.mesnotes_api.dto.SujetDTO;
import com.mesnotes.mesnotes_api.model.Critere;
import com.mesnotes.mesnotes_api.model.Periode;
import com.mesnotes.mesnotes_api.repository.PeriodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodeService {

    @Autowired
    private PeriodeRepository pr;

    @Transactional
    public Double calculerMoyenne(String periodeId) {
        Periode periode = pr.findById(periodeId)
                .orElseThrow(() -> new RuntimeException("Période non trouvée avec l'ID : " + periodeId));

        List<Sujet> sujets = periode.getListeSujets();

        if (sujets == null || sujets.isEmpty()) {
            return 0.0;
        }

        double sommeNotesPonderees = 0.0;
        double sommePoids = 0.0;

        for (Sujet sujet : sujets) {
            if (sujet.getNote() != null && sujet.getPoids() != null ) {
                sommeNotesPonderees += (sujet.getNote() * sujet.getPoids());
                sommePoids += sujet.getPoids();
            }
        }

        double noteFinale = (sommePoids > 0) ? (sommeNotesPonderees / sommePoids) : 0.0;

        periode.setNote(noteFinale);
        pr.save(periode);

        return noteFinale;
    }

    @Transactional
    public void atteindreNote(String periodeId, Double objectif) {
        Periode periode = pr.findById(periodeId)
                .orElseThrow(() -> new RuntimeException("Période non trouvée"));

        List<Sujet> sujets = periode.getListeSujets();
        if (sujets.isEmpty()) return;

        double pointsActuels = 0.0;
        double poidsSujetsVides = 0.0;
        double poidsTotal = 0.0;

        for (Sujet s : sujets) {
            poidsTotal += s.getPoids();
            if (s.getNote() != null && s.getNote() > 0) {
                pointsActuels += (s.getNote() * s.getPoids());
            } else {
                poidsSujetsVides += s.getPoids();
            }
        }

        double pointsNecessaires = (objectif * poidsTotal) - pointsActuels;

        if (pointsNecessaires <= 0) return; 

        if (poidsSujetsVides > 0) {
            double noteNecessairePourVides = pointsNecessaires / poidsSujetsVides;

            for (Sujet s : sujets) {
                if (s.getNote() == null || s.getNote() == 0) {
                    s.setNote(noteNecessairePourVides);
                }
            }
        }

        periode.setNote(objectif);
        pr.save(periode);
    }

// Traitement DTO
    @Autowired
    private SujetService sujetService;

    public PeriodeDTO obtenirPeriodeComplete(String id) {    
        Periode periode = pr.findById(id)
                .orElseThrow(() -> new RuntimeException("Période non trouvée"));
        return entityToDto(periode);
    }

    public PeriodeDTO entityToDto(Periode periode) {
        PeriodeDTO dto = new PeriodeDTO();
        dto.setId(periode.getId());
        dto.setNom(periode.getNom());
        dto.setNote(periode.getNote());
        dto.setPoids(periode.getPoids());
        if (periode.getListeSujets() != null) {
            List<SujetDTO> sujetDtos = periode.getListeSujets().stream()
                .map(sujetService::entityToDto)
                .collect(Collectors.toList());
            dto.setListeSujets(sujetDtos);
        }
        return dto;
    } 
}
