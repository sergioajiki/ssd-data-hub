package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.ImportarFiocruzUseCase;
import br.gov.ssd.datahub.application.port.out.FiocruzRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportarFiocruzUseCaseImpl implements ImportarFiocruzUseCase {

    private final FiocruzRepositoryPort repositoryPort;

    @Override
    public void importar(List<AtendimentoFiocruz> atendimentos) {
        repositoryPort.salvarTodos(atendimentos);
    }
}
