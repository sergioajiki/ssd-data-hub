package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.RelatoCasoTelessaudeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TelessaudeJpaRepository extends JpaRepository<RelatoCasoTelessaudeEntity, Long> {

    @Query("SELECT t.rowId FROM RelatoCasoTelessaudeEntity t WHERE t.rowId IS NOT NULL")
    Set<String> findAllRowIds();
}
