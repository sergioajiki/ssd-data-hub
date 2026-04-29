package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.AtendimentoFiocruzEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface FiocruzJpaRepository extends JpaRepository<AtendimentoFiocruzEntity, Long> {

    @Query("SELECT f.chaveDeduplicacao FROM AtendimentoFiocruzEntity f")
    Set<String> findAllChavesDeduplicacao();
}
