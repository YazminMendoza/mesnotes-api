package com.mesnotes.mesnotes_api.controller;

import com.mesnotes.mesnotes_api.dto.PeriodeDTO;
import com.mesnotes.mesnotes_api.model.Periode;
import com.mesnotes.mesnotes_api.service.PeriodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/periodes")
@CrossOrigin(origins = "*")
public class PeriodeController {
    @Autowired
    private PeriodeService ps;
    
//(GET) Récupérer des informations sur une période précise    
    @GetMapping("/{id}") 
    public ResponseEntity<PeriodeDTO> getPeriode(@PathVariable String id) {
        return ResponseEntity.ok(ps.obtenirPeriodeComplete(id));
    }
    
//(POST) Créer une nouvelle période
    @PostMapping
    public ResponseEntity<PeriodeDTO> creer(@RequestBody Periode periode) {
        return ResponseEntity.ok(ps.save(periode));
    }

//(PUT) Calculer la moyenne
    @PutMapping("/{id}/calculer")
    public ResponseEntity<Double> calculer(@PathVariable String id) {
        return ResponseEntity.ok(ps.calculerMoyenne(id));
    }

//(POST) Calculer les notes minimales pour atteindre une note cible pour une Période
    @PostMapping("/{id}/atteindre")
    public ResponseEntity<Void> atteindre(@PathVariable String id, @RequestBody Map<String, Double> body) {
        ps.atteindreNote(id, body.get("objectif"));
        return ResponseEntity.ok().build();
    }
    
}
