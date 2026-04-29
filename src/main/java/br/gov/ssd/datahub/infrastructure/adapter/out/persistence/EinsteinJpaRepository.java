package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.SolicitacaoEinsteinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface EinsteinJpaRepository extends JpaRepository<SolicitacaoEinsteinEntity, Long> {

    @Query("SELECT e.idSolicitacao FROM SolicitacaoEinsteinEntity e WHERE e.idSolicitacao IS NOT NULL")
    Set<Long> findAllIdSolicitacao();
}
