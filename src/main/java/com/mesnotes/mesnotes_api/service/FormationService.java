package com.mesnotes.mesnotes_api.service;

import com.mesnotes.mesnotes_api.model.Periode;
import com.mesnotes.mesnotes_api.model.Sujet;
import com.mesnotes.mesnotes_api.dto.FormationDTO;
import com.mesnotes.mesnotes_api.dto.PeriodeDTO;
import com.mesnotes.mesnotes_api.dto.SujetDTO;
import com.mesnotes.mesnotes_api.model.Formation;
import com.mesnotes.mesnotes_api.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationService {
    
    @Autowired
    private FormationRepository fr;

    @Transactional
    public Double calculerMoyenne(String formationId) {        
        Formation formation = fr.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée avec l'ID : " + formationId));

        List<Periode> periodes = formation.getListePeriodes();

        if (periodes == null || periodes.isEmpty()) {
            return formation.getNote();
        }

        double sommeNotesPonderees = 0.0;
        double sommePoids = 0.0;

        for (Periode periode : periodes) {
            if (periode.getNote() != null && periode.getPoids() != null) {
                sommeNotesPonderees += (periode.getNote() * periode.getPoids());
                sommePoids += periode.getPoids();
            }
        }

        double noteFinale = (sommePoids > 0) ? (sommeNotesPonderees / sommePoids) : 0.0;

        formation.setNote(noteFinale);
        fr.save(formation);

        return noteFinale;
    }

    @Transactional
    public void atteindreNote(String formationId, Double objectif) {
        Formation formation = fr.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        List<Periode> periodes = formation.getListePeriodes();
        if (periodes.isEmpty()) return;

        double pointsActuels = 0.0;
        double poidsCriteresVides = 0.0;
        double poidsTotal = 0.0;
        
        for (Periode p : periodes) {
            poidsTotal += p.getPoids();
            if (p.getNote() != null && p.getNote() > 0) {
                pointsActuels += (p.getNote() * p.getPoids());
            } else {
                poidsCriteresVides += p.getPoids();
            }
        }

        double pointsNecessaires = (objectif * poidsTotal) - pointsActuels;

        if (pointsNecessaires <= 0) return; 

        if (poidsCriteresVides > 0) {
            double noteNecessairePourVides = pointsNecessaires / poidsCriteresVides;

            for (Periode p : periodes) {
                if (p.getNote() == null || p.getNote() == 0) {
                    p.setNote(noteNecessairePourVides);
                }
            }
        }

        formation.setNote(objectif);
        fr.save(formation);
    }

// Traitement DTO
    @Autowired
    private PeriodeService periodeService;
    
    public FormationDTO obtenirFormationComplete(String id) {    
        Formation formation = fr.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        return entityToDto(formation);
    }

    public FormationDTO entityToDto(Formation formation) {
        FormationDTO dto = new FormationDTO();
        dto.setId(formation.getId());
        dto.setNom(formation.getNom());
        dto.setNote(formation.getNote());
        if (formation.getListePeriodes() != null) {
            List<PeriodeDTO> periodeDtos = formation.getListePeriodes().stream()
                .map(periodeService::entityToDto)
                .collect(Collectors.toList());
            dto.setListePeriodes(periodeDtos);
        }
        return dto;
    }       
}
