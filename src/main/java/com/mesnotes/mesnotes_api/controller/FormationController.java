package com.mesnotes.mesnotes_api.controller;

import com.mesnotes.mesnotes_api.dto.FormationDTO;
import com.mesnotes.mesnotes_api.model.Formation;
import com.mesnotes.mesnotes_api.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/formations")
public class FormationController {
    @Autowired
    private FormationService fs;

//(GET) Récupérer des informations sur une formation précise    
    @GetMapping("/{id}")
    public ResponseEntity<FormationDTO> getFormation(@PathVariable UUID id) {
        return ResponseEntity.ok(fs.obtenirFormationComplete(id));
    }

//(POST) Créer une nouvelle formation
    @PostMapping
    public ResponseEntity<FormationDTO> creer(@RequestBody Formation formation) {
        return ResponseEntity.ok(fs.save(formation));
    }

//(PUT) Calculer la moyenne
    @PutMapping("/{id}/calculer")
    public ResponseEntity<Double> calculer(@PathVariable UUID id) {
        return ResponseEntity.ok(fs.calculerMoyenne(id));
    }

//(POST) Calculer les notes minimales pour atteindre une note cible pour une Formation
    @PostMapping("/{id}/atteindre")
    public ResponseEntity<Void> atteindre(@PathVariable UUID id, @RequestBody Map<String, Double> body) {
        fs.atteindreNote(id, body.get("objectif"));
        return ResponseEntity.ok().build();
    }

//(PUT) Modifier une formation existente
    @PutMapping("/{id}")
    public ResponseEntity<FormationDTO> modifier(@PathVariable UUID id, @RequestBody Formation formation) {
        formation.setId(id);
        return ResponseEntity.ok(fs.save(formation));
    }

// (DELETE) Effacer une formation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable UUID id) {
        fs.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
