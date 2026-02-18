package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Critere;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CritereRepository extends JpaRepository<Critere, String>{
    List<Critere> findBySujetId(String sujetId);
}
