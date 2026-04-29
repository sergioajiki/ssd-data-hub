package br.gov.ssd.datahub.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
public class AtendimentoNormalizado {

    private final FonteDados fonte;
    private final String idOrigem;

    // Identificação do paciente
    private final String cpf;
    private final String cns;
    private final String nomePaciente;
    private final LocalDate dataNascimento;
    private final String sexo;

    // Localização
    private final String municipio;

    // Contexto clínico
    private final String especialidade;
    private final String cid10Principal;
    private final String cid10Secundario;

    // Evento
    private final LocalDate dataEvento;
    private final String status;
    private final String tipoServico;

    // Rastreabilidade
    private final String sourceFile;

    // Campos extras da fonte (não canônicos — equipamentos futuros entram aqui)
    private final Map<String, Object> dadosExtras;
}
