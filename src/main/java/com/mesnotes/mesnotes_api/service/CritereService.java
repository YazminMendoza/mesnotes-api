package com.mesnotes.mesnotes_api.service;

import com.mesnotes.mesnotes_api.model.Critere;
import com.mesnotes.mesnotes_api.repository.CritereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CritereService {
    
    @Autowired
    private CritereRepository cr;

    @Transactional
    public Critere save(Critere critere) {
        return cr.save(critere);
    }

    @Transactional
    public void delete(String id) {
        cr.deleteById(id);
    }

    @Transactional
    public Critere updateNote(String id, Double nouvelleNote) {
        Critere c = cr.findById(id)
            .orElseThrow(() -> new RuntimeException("Critère non trouvé"));
        c.setNote(nouvelleNote);
        return cr.save(c);
    }
}
