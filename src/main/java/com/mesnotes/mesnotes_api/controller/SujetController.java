package com.mesnotes.mesnotes_api.controller;

import com.mesnotes.mesnotes_api.dto.SujetDTO;
import com.mesnotes.mesnotes_api.model.Sujet;
import com.mesnotes.mesnotes_api.service.SujetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController 
@RequestMapping("/api/sujets")
@CrossOrigin(origins = "*")
public class SujetController {
    @Autowired
    private SujetService ss;

// (GET) Récupérer des informations sur un sujet précis
    @GetMapping("/{id}") 
    public ResponseEntity<SujetDTO> getSujet(@PathVariable UUID id) {
        return ResponseEntity.ok(ss.obtenirSujetComplet(id));
    }    

// (POST) Créer un nouveau sujet avec ou sans critères initiaux
    @PostMapping
    public ResponseEntity<SujetDTO> creer(@RequestBody Sujet sujet) {
        return ResponseEntity.ok(ss.save(sujet));
    }

// (PUT) Calculer la moyenne pondérée en fonction des critères
    @PutMapping("/{id}/calculer")
    public ResponseEntity<Double> calculer(@PathVariable UUID id) {
        return ResponseEntity.ok(ss.calculerMoyenne(id));
    }

// (POST) Calculer les notes minimales pour atteindre une note cible
    @PostMapping("/{id}/atteindre")
    public ResponseEntity<Void> atteindre(@PathVariable UUID id, @RequestBody Map<String, Double> body) {
        ss.atteindreNote(id, body.get("objectif"));
        return ResponseEntity.ok().build();
    }

// (PUT) Modifier un sujet existant
    @PutMapping("/{id}")
    public ResponseEntity<SujetDTO> modifier(@PathVariable UUID id, @RequestBody Sujet sujet) {
        sujet.setId(id);
        return ResponseEntity.ok(ss.save(sujet));
    }

// (DELETE) Effacer un sujet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable UUID id) {
        ss.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
