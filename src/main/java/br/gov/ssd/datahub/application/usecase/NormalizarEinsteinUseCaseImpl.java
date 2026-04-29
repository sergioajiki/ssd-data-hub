package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.NormalizarEinsteinUseCase;
import br.gov.ssd.datahub.application.port.out.AtendimentoNormalizadoRepositoryPort;
import br.gov.ssd.datahub.application.port.out.EinsteinRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoNormalizado;
import br.gov.ssd.datahub.domain.model.FonteDados;
import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalizarEinsteinUseCaseImpl implements NormalizarEinsteinUseCase {

    private final EinsteinRepositoryPort einsteinPort;
    private final AtendimentoNormalizadoRepositoryPort normalizadoPort;

    @Override
    public long executar() {
        List<SolicitacaoEinstein> solicitacoes = einsteinPort.buscarTodos();
        log.info("Einstein: normalizando {} registros", solicitacoes.size());

        List<AtendimentoNormalizado> normalizados = solicitacoes.stream()
                .map(this::normalizar)
                .toList();

        normalizadoPort.salvarTodos(normalizados);
        return normalizadoPort.contarPorFonte(FonteDados.EINSTEIN);
    }

    private AtendimentoNormalizado normalizar(SolicitacaoEinstein s) {
        Map<String, Object> extras = new HashMap<>();
        if (s.getDesfecho() != null)             extras.put("desfecho", s.getDesfecho());
        if (s.getRecomendacoesMedicas() != null)  extras.put("recomendacoesMedicas", s.getRecomendacoesMedicas());
        if (s.getHistoriaClinica() != null)        extras.put("historiaClinica", s.getHistoriaClinica());
        if (s.getMedicamentosEmUso() != null)      extras.put("medicamentosEmUso", s.getMedicamentosEmUso());
        if (s.getResumoClinico() != null)          extras.put("resumoClinico", s.getResumoClinico());
        if (s.getTelesolicitante() != null)        extras.put("telesolicitante", s.getTelesolicitante());
        if (s.getTeleconsultor() != null)          extras.put("teleconsultor", s.getTeleconsultor());
        if (s.getNomeMae() != null)                extras.put("nomeMae", s.getNomeMae());

        return AtendimentoNormalizado.builder()
                .fonte(FonteDados.EINSTEIN)
                .idOrigem(s.getIdSolicitacao() != null ? s.getIdSolicitacao().toString() : null)
                .cpf(s.getCpf())
                .cns(s.getCns())
                .nomePaciente(s.getNomePaciente())
                .dataNascimento(s.getDataNascimento())
                .sexo(s.getSexo())
                .municipio(null)
                .especialidade(s.getEspecialidadeSolicitada())
                .cid10Principal(s.getCid10Principal())
                .cid10Secundario(s.getCid10Secundario())
                .dataEvento(s.getHoraSolicitado() != null ? s.getHoraSolicitado().toLocalDate() : null)
                .status(s.getStatus())
                .tipoServico("Teleconsulta")
                .sourceFile(s.getSourceFile())
                .dadosExtras(extras)
                .build();
    }
}
