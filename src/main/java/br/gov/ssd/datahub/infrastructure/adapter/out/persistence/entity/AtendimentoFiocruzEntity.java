package br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atendimento_fiocruz", uniqueConstraints = {
        @UniqueConstraint(name = "uk_atendimento_fiocruz_chave", columnNames = "chave_deduplicacao")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoFiocruzEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chave_deduplicacao", nullable = false)
    private String chaveDeduplicacao;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "tipo_servico")
    private String tipoServico;

    @Column(name = "data_agendamento")
    private LocalDate dataAgendamento;

    @Column(name = "hora_atendimento")
    private LocalTime horaAtendimento;

    private String estabelecimento;
    private String especialidade;

    @Column(name = "especialidade_medico")
    private String especialidadeMedico;

    @Column(name = "cpf_medico")
    private String cpfMedico;

    @Column(name = "cbo_medico")
    private String cboMedico;

    @Column(name = "status_consulta")
    private String statusConsulta;

    private String municipio;

    @Column(name = "cpf_paciente")
    private String cpfPaciente;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "cns_paciente")
    private String cnsPaciente;

    @Column(name = "raca_paciente")
    private String racaPaciente;

    @Column(name = "etnia_paciente")
    private String etniaPaciente;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "cid_consulta")
    private String cidConsulta;

    private String telefone;

    @Column(name = "tipo_zona")
    private String tipoZona;

    @Column(name = "endereco_completo")
    private String enderecoCompleto;

    private String rua;
    private String cep;

    @Column(name = "num_imovel")
    private String numImovel;

    private String bairro;
    private String complemento;

    @Column(name = "classificacao_conclusao")
    private String classificacaoConclusao;

    @Column(name = "descricao_endereco")
    private String descricaoEndereco;

    @Column(name = "source_file")
    private String sourceFile;
}
