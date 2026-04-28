package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.ImportarEinsteinUseCase;
import br.gov.ssd.datahub.application.port.out.EinsteinRepositoryPort;
import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportarEinsteinUseCaseImpl implements ImportarEinsteinUseCase {

    private final EinsteinRepositoryPort repositoryPort;

    @Override
    public void importar(List<SolicitacaoEinstein> solicitacoes) {
        repositoryPort.salvarTodos(solicitacoes);
    }
}
