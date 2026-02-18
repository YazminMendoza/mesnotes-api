package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Periode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodeRepository extends JpaRepository<Periode, String> {
    List<Periode> findByFormationId(String formationId);
}
