package br.gov.ssd.datahub.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class SolicitacaoEinstein {

    private final Long idSolicitacao;
    private final String status;

    // Profissionais envolvidos
    private final String telesolicitante;
    private final String teleconsultor;
    private final String finalizador;
    private final String teleregulador;

    // Timestamps
    private final LocalDateTime horaSolicitado;
    private final LocalDateTime horaConsultado;
    private final LocalDateTime horaFinalizado;
    private final LocalDateTime horaAuditado;
    private final LocalDateTime horaModificado;
    private final LocalDateTime agendamentoConfirmado;
    private final LocalDateTime agendamentoCancelado;

    // Dados do paciente
    private final String nomePaciente;
    private final String paisNascimento;
    private final String cns;
    private final String cpf;
    private final LocalDate dataNascimento;
    private final Integer idade;
    private final Integer idadeEmDias;
    private final String sexo;
    private final String nomeMae;

    // Dados da solicitação
    private final String especialidadeSolicitada;
    private final String nomeMedicoTelesolicitante;
    private final String nomeMedicoSolicitante;
    private final String crmMedicoSolicitante;

    // Conteúdo clínico
    private final String medicamentosEmUso;
    private final String historiaClinica;
    private final String cid10Principal;
    private final String cid10Secundario;
    private final String recomendacoesMedicas;
    private final String resumoClinico;
    private final String desfecho;

    // Rastreabilidade
    private final String sourceFile;
}
