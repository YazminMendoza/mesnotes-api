package com.mesnotes.mesnotes_api.service;

import com.mesnotes.mesnotes_api.dto.CritereDTO;
import com.mesnotes.mesnotes_api.model.Critere;
import com.mesnotes.mesnotes_api.repository.CritereRepository;
import com.mesnotes.mesnotes_api.repository.SujetRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CritereService {
    
    @Autowired
    private CritereRepository cr;

    @Autowired
    @Lazy
    private SujetService ss;

    @Transactional
    public CritereDTO save(Critere critere) {
        if (critere.getId() != null) {
            Critere original = cr.findById(critere.getId()).orElse(null);
            if (original != null && critere.getSujet() == null) {
                critere.setSujet(original.getSujet());
            }
        }
        Critere sauve = cr.save(critere);
        if (sauve.getSujet() != null) {
            ss.calculerMoyenne(sauve.getSujet().getId());
        }
        return entityToDto(sauve);
    }

    @Transactional
    public void deleteById(UUID id) {
// Avant d'effacer, on va chercher le Sujet du Critère pour recalculer sa moyenne
        Critere critere = cr.findById(id)
            .orElseThrow(() -> new RuntimeException("Critère non trouvé"));
        UUID sujetId = critere.getSujet().getId();
        cr.delete(critere);
        cr.flush();
        ss.calculerMoyenne(sujetId);        
    }

    public CritereDTO entityToDto(Critere critere) {
        CritereDTO dto = new CritereDTO();
        dto.setId(critere.getId());
        dto.setNom(critere.getNom());
        dto.setNote(critere.getNote());
        dto.setPoids(critere.getPoids());
        return dto;
    }
}
