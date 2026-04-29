package br.gov.ssd.datahub.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AtendimentoFiocruz {

    private final LocalDate dataSolicitacao;
    private final String tipoServico;
    private final LocalDate dataAgendamento;
    private final LocalTime horaAtendimento;
    private final String estabelecimento;
    private final String especialidade;
    private final String especialidadeMedico;
    private final String cpfMedico;
    private final String cboMedico;
    private final String statusConsulta;
    private final String municipio;

    // Dados do paciente
    private final String cpfPaciente;
    private final String nomePaciente;
    private final String cnsPaciente;
    private final String racaPaciente;
    private final String etniaPaciente;
    private final LocalDate dataNascimento;
    private final String cidConsulta;
    private final String telefone;

    // Endereço
    private final String tipoZona;
    private final String enderecoCompleto;
    private final String rua;
    private final String cep;
    private final String numImovel;
    private final String bairro;
    private final String complemento;
    private final String descricaoEndereco;

    // Conclusão e rastreabilidade
    private final String classificacaoConclusao;
    private final String sourceFile;
}
