package br.gov.ssd.datahub.application.port.in;

import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;

import java.util.List;

public interface ImportarTelessaudeUseCase {

    void importar(List<RelatoCasoTelessaude> relatos);
}
