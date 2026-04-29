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

    @Override
    public List<SolicitacaoEinstein> buscarTodos() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    private SolicitacaoEinstein toDomain(SolicitacaoEinsteinEntity e) {
        return SolicitacaoEinstein.builder()
                .idSolicitacao(e.getIdSolicitacao())
                .status(e.getStatus())
                .telesolicitante(e.getTelesolicitante())
                .teleconsultor(e.getTeleconsultor())
                .finalizador(e.getFinalizador())
                .teleregulador(e.getTeleregulador())
                .horaSolicitado(e.getHoraSolicitado())
                .horaConsultado(e.getHoraConsultado())
                .horaFinalizado(e.getHoraFinalizado())
                .horaAuditado(e.getHoraAuditado())
                .horaModificado(e.getHoraModificado())
                .agendamentoConfirmado(e.getAgendamentoConfirmado())
                .agendamentoCancelado(e.getAgendamentoCancelado())
                .nomePaciente(e.getNomePaciente())
                .paisNascimento(e.getPaisNascimento())
                .cns(e.getCns())
                .cpf(e.getCpf())
                .dataNascimento(e.getDataNascimento())
                .idade(e.getIdade())
                .idadeEmDias(e.getIdadeEmDias())
                .sexo(e.getSexo())
                .nomeMae(e.getNomeMae())
                .especialidadeSolicitada(e.getEspecialidadeSolicitada())
                .nomeMedicoTelesolicitante(e.getNomeMedicoTelesolicitante())
                .nomeMedicoSolicitante(e.getNomeMedicoSolicitante())
                .crmMedicoSolicitante(e.getCrmMedicoSolicitante())
                .medicamentosEmUso(e.getMedicamentosEmUso())
                .historiaClinica(e.getHistoriaClinica())
                .cid10Principal(e.getCid10Principal())
                .cid10Secundario(e.getCid10Secundario())
                .recomendacoesMedicas(e.getRecomendacoesMedicas())
                .resumoClinico(e.getResumoClinico())
                .desfecho(e.getDesfecho())
                .sourceFile(e.getSourceFile())
                .build();
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
