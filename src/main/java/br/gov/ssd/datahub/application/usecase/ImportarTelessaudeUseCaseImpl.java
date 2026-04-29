package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.ImportarTelessaudeUseCase;
import br.gov.ssd.datahub.application.port.out.TelessaudeRepositoryPort;
import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportarTelessaudeUseCaseImpl implements ImportarTelessaudeUseCase {

    private final TelessaudeRepositoryPort repositoryPort;

    @Override
    public void importar(List<RelatoCasoTelessaude> relatos) {
        repositoryPort.salvarTodos(relatos);
    }
}
