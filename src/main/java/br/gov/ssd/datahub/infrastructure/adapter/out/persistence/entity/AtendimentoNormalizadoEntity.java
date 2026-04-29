package br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "atendimento_normalizado", uniqueConstraints = {
        @UniqueConstraint(name = "uk_atendimento_normalizado_fonte_origem", columnNames = {"fonte", "id_origem"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoNormalizadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fonte;

    @Column(name = "id_origem", nullable = false)
    private String idOrigem;

    private String cpf;
    private String cns;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String sexo;
    private String municipio;
    private String especialidade;

    @Column(name = "cid10_principal")
    private String cid10Principal;

    @Column(name = "cid10_secundario")
    private String cid10Secundario;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    private String status;

    @Column(name = "tipo_servico")
    private String tipoServico;

    @Column(name = "source_file")
    private String sourceFile;

    @Column(name = "dados_extras", columnDefinition = "TEXT")
    private String dadosExtras;
}
