package br.gov.ssd.datahub.application.port.out;

import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;

import java.util.List;

public interface TelessaudeRepositoryPort {

    void salvarTodos(List<RelatoCasoTelessaude> relatos);

    List<RelatoCasoTelessaude> buscarTodos();
}
