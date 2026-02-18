package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Sujet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SujetRepository extends JpaRepository<Sujet, String> {
    List<Sujet> findByPeriodeId(String periodeId);
}
