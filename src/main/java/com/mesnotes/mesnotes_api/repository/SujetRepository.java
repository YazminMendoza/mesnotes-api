package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Sujet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SujetRepository extends JpaRepository<Sujet, UUID> {
    List<Sujet> findByPeriodeId(UUID periodeId);
}
