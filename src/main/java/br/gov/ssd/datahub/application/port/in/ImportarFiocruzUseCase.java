package br.gov.ssd.datahub.application.port.in;

import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;

import java.util.List;

public interface ImportarFiocruzUseCase {

    void importar(List<AtendimentoFiocruz> atendimentos);
}
