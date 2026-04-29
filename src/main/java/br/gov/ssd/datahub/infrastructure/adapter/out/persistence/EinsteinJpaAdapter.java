package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.application.port.out.EinsteinRepositoryPort;
import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.SolicitacaoEinsteinEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class EinsteinJpaAdapter implements EinsteinRepositoryPort {

    private final EinsteinJpaRepository repository;

    @Override
    public void salvarTodos(List<SolicitacaoEinstein> solicitacoes) {
        Set<Long> existentes = repository.findAllIdSolicitacao();

        List<SolicitacaoEinsteinEntity> novas = solicitacoes.stream()
                .filter(s -> s.getIdSolicitacao() == null || !existentes.contains(s.getIdSolicitacao()))
                .map(this::toEntity)
                .toList();

        int duplicados = solicitacoes.size() - novas.size();
        if (duplicados > 0) {
            log.info("Einstein: {} registros duplicados ignorados", duplicados);
        }

        repository.saveAll(novas);
        log.info("Einstein: {} registros persistidos", novas.size());
    }

    private SolicitacaoEinsteinEntity toEntity(SolicitacaoEinstein s) {
        return SolicitacaoEinsteinEntity.builder()
                .idSolicitacao(s.getIdSolicitacao())
                .status(s.getStatus())
                .telesolicitante(s.getTelesolicitante())
                .teleconsultor(s.getTeleconsultor())
                .finalizador(s.getFinalizador())
                .teleregulador(s.getTeleregulador())
                .horaSolicitado(s.getHoraSolicitado())
                .horaConsultado(s.getHoraConsultado())
                .horaFinalizado(s.getHoraFinalizado())
                .horaAuditado(s.getHoraAuditado())
                .horaModificado(s.getHoraModificado())
                .agendamentoConfirmado(s.getAgendamentoConfirmado())
                .agendamentoCancelado(s.getAgendamentoCancelado())
                .nomePaciente(s.getNomePaciente())
                .paisNascimento(s.getPaisNascimento())
                .cns(s.getCns())
                .cpf(s.getCpf())
                .dataNascimento(s.getDataNascimento())
                .idade(s.getIdade())
                .idadeEmDias(s.getIdadeEmDias())
                .sexo(s.getSexo())
                .nomeMae(s.getNomeMae())
                .especialidadeSolicitada(s.getEspecialidadeSolicitada())
                .nomeMedicoTelesolicitante(s.getNomeMedicoTelesolicitante())
                .nomeMedicoSolicitante(s.getNomeMedicoSolicitante())
                .crmMedicoSolicitante(s.getCrmMedicoSolicitante())
                .medicamentosEmUso(s.getMedicamentosEmUso())
                .historiaClinica(s.getHistoriaClinica())
                .cid10Principal(s.getCid10Principal())
                .cid10Secundario(s.getCid10Secundario())
                .recomendacoesMedicas(s.getRecomendacoesMedicas())
                .resumoClinico(s.getResumoClinico())
                .desfecho(s.getDesfecho())
                .sourceFile(s.getSourceFile())
                .build();
    }
}
