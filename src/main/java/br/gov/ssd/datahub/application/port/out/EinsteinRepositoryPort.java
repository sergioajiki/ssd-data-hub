package br.gov.ssd.datahub.application.port.out;

import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;

import java.util.List;

public interface EinsteinRepositoryPort {

    void salvarTodos(List<SolicitacaoEinstein> solicitacoes);
}
