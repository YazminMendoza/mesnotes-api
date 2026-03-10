package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Periode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PeriodeRepository extends JpaRepository<Periode, UUID> {
    List<Periode> findByFormationId(UUID formationId);
}
