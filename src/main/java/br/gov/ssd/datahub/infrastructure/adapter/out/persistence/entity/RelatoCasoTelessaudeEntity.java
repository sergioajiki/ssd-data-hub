package br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "relato_caso_telessaude", uniqueConstraints = {
        @UniqueConstraint(name = "uk_relato_caso_telessaude_row_id", columnNames = "row_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatoCasoTelessaudeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_id")
    private String rowId;

    @Column(name = "email_profissional")
    private String emailProfissional;

    @Column(name = "nome_profissional")
    private String nomeProfissional;

    @Column(name = "motivo_relato", columnDefinition = "TEXT")
    private String motivoRelato;

    private String municipio;

    @Column(name = "local_atendimento")
    private String localAtendimento;

    @Column(name = "data_relato")
    private LocalDate dataRelato;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    private String cpf;
    private String cns;

    @Column(name = "contato_paciente")
    private String contatoPaciente;

    private Integer idade;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String sexo;

    @Column(name = "cor_pele")
    private String corPele;

    @Column(name = "queixa_principal", columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column(name = "localizacao_anatomica")
    private String localizacaoAnatomica;

    @Column(name = "lado_acometimento")
    private String ladoAcometimento;

    private String coloracao;
    private String consistencia;
    private String superficie;
    private String insercao;
    private String tamanho;

    @Column(name = "lesao_fundamental")
    private String lesaoFundamental;

    private String sintomatologia;

    @Column(name = "palpacao_linfonodal")
    private String palpacaoLinfonodal;

    @Column(name = "caracteristicas_clinicas", columnDefinition = "TEXT")
    private String caracteristicasClinicas;

    @Column(name = "tempo_evolucao")
    private String tempoEvolucao;

    @Column(name = "historia_doenca_atual", columnDefinition = "TEXT")
    private String historiaDoencaAtual;

    @Column(name = "doenca_preexistente", columnDefinition = "TEXT")
    private String doencaPreexistente;

    @Column(name = "habitos_vicios")
    private String habitosVicios;

    @Column(name = "hipotese_principal")
    private String hipotesePrincipal;

    @Column(name = "hipotese_secundaria")
    private String hipoteseSecundaria;

    @Column(name = "realizou_biopsia")
    private String realizouBiopsia;

    @Column(name = "tipo_biopsia")
    private String tipoBiopsia;

    @Column(name = "data_biopsia")
    private LocalDate dataBiopsia;

    @Column(name = "cid10")
    private String cid10;

    @Column(name = "hipotese_principal_consultor")
    private String hipotesePrincipalConsultor;

    @Column(name = "hipotese_secundaria_consultor")
    private String hipoteseSecundariaConsultor;

    @Column(name = "conduta_sugerida", columnDefinition = "TEXT")
    private String condutaSugerida;

    @Column(name = "coordenacao_cuidado", columnDefinition = "TEXT")
    private String coordenacaoCuidado;

    @Column(name = "qualidade_imagens")
    private String qualidadeImagens;

    @Column(name = "lesao_suspeita")
    private String lesaoSuspeita;

    @Column(name = "consultor_responsavel")
    private String consultorResponsavel;

    @Column(name = "email_consultor")
    private String emailConsultor;

    @Column(name = "data_resposta")
    private LocalDate dataResposta;

    @Column(name = "source_file")
    private String sourceFile;
}
