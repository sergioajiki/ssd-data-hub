package br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacao_einstein")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoEinsteinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_solicitacao")
    private Long idSolicitacao;

    private String status;
    private String telesolicitante;
    private String teleconsultor;
    private String finalizador;
    private String teleregulador;

    @Column(name = "hora_solicitado")
    private LocalDateTime horaSolicitado;

    @Column(name = "hora_consultado")
    private LocalDateTime horaConsultado;

    @Column(name = "hora_finalizado")
    private LocalDateTime horaFinalizado;

    @Column(name = "hora_auditado")
    private LocalDateTime horaAuditado;

    @Column(name = "hora_modificado")
    private LocalDateTime horaModificado;

    @Column(name = "agendamento_confirmado")
    private LocalDateTime agendamentoConfirmado;

    @Column(name = "agendamento_cancelado")
    private LocalDateTime agendamentoCancelado;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "pais_nascimento")
    private String paisNascimento;

    private String cns;
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private Integer idade;

    @Column(name = "idade_em_dias")
    private Integer idadeEmDias;

    private String sexo;

    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "especialidade_solicitada")
    private String especialidadeSolicitada;

    @Column(name = "nome_medico_telesolicitante", length = 500)
    private String nomeMedicoTelesolicitante;

    @Column(name = "nome_medico_solicitante", length = 500)
    private String nomeMedicoSolicitante;

    @Column(name = "crm_medico_solicitante")
    private String crmMedicoSolicitante;

    @Column(name = "medicamentos_em_uso", columnDefinition = "TEXT")
    private String medicamentosEmUso;

    @Column(name = "historia_clinica", columnDefinition = "TEXT")
    private String historiaClinica;

    @Column(name = "cid10_principal")
    private String cid10Principal;

    @Column(name = "cid10_secundario")
    private String cid10Secundario;

    @Column(name = "recomendacoes_medicas", columnDefinition = "TEXT")
    private String recomendacoesMedicas;

    @Column(name = "resumo_clinico", columnDefinition = "TEXT")
    private String resumoClinico;

    @Column(columnDefinition = "TEXT")
    private String desfecho;

    @Column(name = "source_file")
    private String sourceFile;
}
