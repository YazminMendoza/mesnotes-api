package com.mesnotes.mesnotes_api.repository;

import com.mesnotes.mesnotes_api.model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FormationRepository extends JpaRepository<Formation, UUID> {

}
