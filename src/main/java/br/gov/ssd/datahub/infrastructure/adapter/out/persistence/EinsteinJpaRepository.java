package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.SolicitacaoEinsteinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EinsteinJpaRepository extends JpaRepository<SolicitacaoEinsteinEntity, Long> {
}
