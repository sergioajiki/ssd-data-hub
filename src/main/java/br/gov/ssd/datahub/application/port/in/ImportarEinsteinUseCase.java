package br.gov.ssd.datahub.application.port.in;

import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;

import java.util.List;

public interface ImportarEinsteinUseCase {

    void importar(List<SolicitacaoEinstein> solicitacoes);
}
