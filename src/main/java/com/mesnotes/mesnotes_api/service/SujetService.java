package com.mesnotes.mesnotes_api.service;

import com.mesnotes.mesnotes_api.dto.CritereDTO;
import com.mesnotes.mesnotes_api.dto.SujetDTO;
import com.mesnotes.mesnotes_api.model.Critere;
import com.mesnotes.mesnotes_api.model.Sujet;
import com.mesnotes.mesnotes_api.repository.SujetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SujetService {

    @Autowired
    private SujetRepository sr;

// Logique métier    
    @Transactional
    public Double calculerMoyenne(String sujetId) {
        // Chercher le sujet et ses critères sur la base de données
        Sujet sujet = sr.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé avec l'ID : " + sujetId));

        List<Critere> criteres = sujet.getListeCriteres();

        // S'il n'y a pas des critères, on retourne la note
        if (criteres == null || criteres.isEmpty()) {
            return sujet.getNote();
        }

        double sommeNotesPonderees = 0.0;
        double sommePoids = 0.0;

        for (Critere critere : criteres) {
            // On somme seulement si le critère a une note (pas null)
            if (critere.getNote() != null && critere.getPoids() != null) {
                sommeNotesPonderees += (critere.getNote() * critere.getPoids());
                sommePoids += critere.getPoids();
            }
        }

        // Calcular la moyenne finale (en evitant la division par zero)
        double noteFinale = (sommePoids > 0) ? (sommeNotesPonderees / sommePoids) : 0.0;

        // Mettre à jour l'objet Sujet et enregistrer dans la BD
        sujet.setNote(noteFinale);
        sr.save(sujet);

        return noteFinale;
    }

    @Transactional
    public void atteindreNote(String sujetId, Double objectif) {
        Sujet sujet = sr.findById(sujetId)
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé"));

        List<Critere> criteres = sujet.getListeCriteres();
        if (criteres.isEmpty()) return;

        double pointsActuels = 0.0;
        double poidsCriteresVides = 0.0;
        double poidsTotal = 0.0;

        // Analiser ce qu'il y a et ce qui manque
        for (Critere c : criteres) {
            poidsTotal += c.getPoids();
            if (c.getNote() != null && c.getNote() > 0) {
                pointsActuels += (c.getNote() * c.getPoids());
            } else {
                poidsCriteresVides += c.getPoids();
            }
        }

        // Calculer les points manquants pour atteindre l'objectif
        double pointsNecessaires = (objectif * poidsTotal) - pointsActuels;

        if (pointsNecessaires <= 0) return; // Si l'objectif est déjà atteint ou dépassé

        // Répartir les points nécessaires entre les critères vides
        if (poidsCriteresVides > 0) {
            double noteNecessairePourVides = pointsNecessaires / poidsCriteresVides;

            for (Critere c : criteres) {
                if (c.getNote() == null || c.getNote() == 0) {
                    // Mettre en place la note nécessaire
                    c.setNote(noteNecessairePourVides);
                }
            }
        }

        // Mettre à jour l'objet Sujet avec la note objectif et le sauvegarder
        sujet.setNote(objectif);
        sr.save(sujet);
    }

// Traitement DTO
    public SujetDTO obtenirSujetComplet(String id) {    
        Sujet sujet = sr.findById(id)
                .orElseThrow(() -> new RuntimeException("Sujet non trouvé"));
        return entityToDto(sujet);
    }

    public SujetDTO entityToDto(Sujet sujet) {
        SujetDTO dto = new SujetDTO();
        dto.setId(sujet.getId());
        dto.setNom(sujet.getNom());
        dto.setNote(sujet.getNote());
        dto.setPoids(sujet.getPoids());
        if (sujet.getListeCriteres() != null) {
            List<CritereDTO> critereDtos = sujet.getListeCriteres().stream()
                .map(this::critereToDto)
                .collect(Collectors.toList());
            dto.setListeCriteres(critereDtos);
        }
        return dto;
    }

    public CritereDTO critereToDto(Critere critere) {
        CritereDTO dto = new CritereDTO();
        dto.setId(critere.getId());
        dto.setNom(critere.getNom());
        dto.setNote(critere.getNote());
        dto.setPoids(critere.getPoids());
        return dto;
    }
}
