package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.application.port.out.TelessaudeRepositoryPort;
import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.RelatoCasoTelessaudeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelessaudeJpaAdapter implements TelessaudeRepositoryPort {

    private final TelessaudeJpaRepository repository;

    @Override
    public void salvarTodos(List<RelatoCasoTelessaude> relatos) {
        Set<String> existentesNoBanco = repository.findAllRowIds();

        Map<String, RelatoCasoTelessaude> unicosNoBatch = new LinkedHashMap<>();
        for (RelatoCasoTelessaude r : relatos) {
            if (r.getRowId() != null) {
                unicosNoBatch.putIfAbsent(r.getRowId(), r);
            }
        }

        List<RelatoCasoTelessaudeEntity> novas = unicosNoBatch.entrySet().stream()
                .filter(e -> !existentesNoBanco.contains(e.getKey()))
                .map(e -> toEntity(e.getValue()))
                .toList();

        int duplicados = relatos.size() - novas.size();
        if (duplicados > 0) {
            log.info("Telessaúde: {} registros duplicados ignorados", duplicados);
        }

        repository.saveAll(novas);
        log.info("Telessaúde: {} registros persistidos", novas.size());
    }

    private RelatoCasoTelessaudeEntity toEntity(RelatoCasoTelessaude r) {
        return RelatoCasoTelessaudeEntity.builder()
                .rowId(r.getRowId())
                .emailProfissional(r.getEmailProfissional())
                .nomeProfissional(r.getNomeProfissional())
                .motivoRelato(r.getMotivoRelato())
                .municipio(r.getMunicipio())
                .localAtendimento(r.getLocalAtendimento())
                .dataRelato(r.getDataRelato())
                .nomePaciente(r.getNomePaciente())
                .cpf(r.getCpf())
                .cns(r.getCns())
                .contatoPaciente(r.getContatoPaciente())
                .idade(r.getIdade())
                .dataNascimento(r.getDataNascimento())
                .sexo(r.getSexo())
                .corPele(r.getCorPele())
                .queixaPrincipal(r.getQueixaPrincipal())
                .localizacaoAnatomica(r.getLocalizacaoAnatomica())
                .ladoAcometimento(r.getLadoAcometimento())
                .coloracao(r.getColoracao())
                .consistencia(r.getConsistencia())
                .superficie(r.getSuperficie())
                .insercao(r.getInsercao())
                .tamanho(r.getTamanho())
                .lesaoFundamental(r.getLesaoFundamental())
                .sintomatologia(r.getSintomatologia())
                .palpacaoLinfonodal(r.getPalpacaoLinfonodal())
                .caracteristicasClinicas(r.getCaracteristicasClinicas())
                .tempoEvolucao(r.getTempoEvolucao())
                .historiaDoencaAtual(r.getHistoriaDoencaAtual())
                .doencaPreexistente(r.getDoencaPreexistente())
                .habitosVicios(r.getHabitosVicios())
                .hipotesePrincipal(r.getHipotesePrincipal())
                .hipoteseSecundaria(r.getHipoteseSecundaria())
                .realizouBiopsia(r.getRealizouBiopsia())
                .tipoBiopsia(r.getTipoBiopsia())
                .dataBiopsia(r.getDataBiopsia())
                .cid10(r.getCid10())
                .hipotesePrincipalConsultor(r.getHipotesePrincipalConsultor())
                .hipoteseSecundariaConsultor(r.getHipoteseSecundariaConsultor())
                .condutaSugerida(r.getCondutaSugerida())
                .coordenacaoCuidado(r.getCoordenacaoCuidado())
                .qualidadeImagens(r.getQualidadeImagens())
                .lesaoSuspeita(r.getLesaoSuspeita())
                .consultorResponsavel(r.getConsultorResponsavel())
                .emailConsultor(r.getEmailConsultor())
                .dataResposta(r.getDataResposta())
                .sourceFile(r.getSourceFile())
                .build();
    }
}
