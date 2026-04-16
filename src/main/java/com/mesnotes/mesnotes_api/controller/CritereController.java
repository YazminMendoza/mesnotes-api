package com.mesnotes.mesnotes_api.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mesnotes.mesnotes_api.dto.CritereDTO;
import com.mesnotes.mesnotes_api.model.Critere;
import com.mesnotes.mesnotes_api.service.CritereService;

@RestController
@RequestMapping("/api/criteres")
@CrossOrigin(origins = "http://localhost:5173")
public class CritereController {
    @Autowired
    private CritereService cs;

// Comme les Critères ne peuvent pas exister dehors les Sujets, sa création est dans SujetController

// (PUT) Modifier un critère existant
    @PutMapping("/{id}")
    public ResponseEntity<CritereDTO> modifier(@PathVariable UUID id, @RequestBody Critere critere) {
        critere.setId(id);
        return ResponseEntity.ok(cs.save(critere));
    }

// (DELETE) Supprimer un critère    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable UUID id) {
        cs.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
