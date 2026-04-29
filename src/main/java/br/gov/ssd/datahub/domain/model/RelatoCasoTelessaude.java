package br.gov.ssd.datahub.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RelatoCasoTelessaude {

    private final String rowId;

    // Profissional solicitante
    private final String emailProfissional;
    private final String nomeProfissional;
    private final String motivoRelato;
    private final String municipio;
    private final String localAtendimento;
    private final LocalDate dataRelato;

    // Dados do paciente
    private final String nomePaciente;
    private final String cpf;
    private final String cns;
    private final String contatoPaciente;
    private final Integer idade;
    private final LocalDate dataNascimento;
    private final String sexo;
    private final String corPele;

    // Características clínicas da lesão
    private final String queixaPrincipal;
    private final String localizacaoAnatomica;
    private final String ladoAcometimento;
    private final String coloracao;
    private final String consistencia;
    private final String superficie;
    private final String insercao;
    private final String tamanho;
    private final String lesaoFundamental;
    private final String sintomatologia;
    private final String palpacaoLinfonodal;
    private final String caracteristicasClinicas;
    private final String tempoEvolucao;
    private final String historiaDoencaAtual;
    private final String doencaPreexistente;
    private final String habitosVicios;

    // Hipóteses diagnósticas do solicitante
    private final String hipotesePrincipal;
    private final String hipoteseSecundaria;

    // Diagnóstico do consultor
    private final String cid10;
    private final String hipotesePrincipalConsultor;
    private final String hipoteseSecundariaConsultor;
    private final String condutaSugerida;
    private final String coordenacaoCuidado;

    // Biópsia
    private final String realizouBiopsia;
    private final String tipoBiopsia;
    private final LocalDate dataBiopsia;

    // Consultor e resposta
    private final String consultorResponsavel;
    private final String emailConsultor;
    private final LocalDate dataResposta;
    private final String qualidadeImagens;
    private final String lesaoSuspeita;

    // Rastreabilidade
    private final String sourceFile;
}
