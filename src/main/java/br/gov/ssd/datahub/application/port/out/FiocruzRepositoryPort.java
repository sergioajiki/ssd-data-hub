package br.gov.ssd.datahub.application.port.out;

import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;

import java.util.List;

public interface FiocruzRepositoryPort {

    void salvarTodos(List<AtendimentoFiocruz> atendimentos);

    List<AtendimentoFiocruz> buscarTodos();
}
