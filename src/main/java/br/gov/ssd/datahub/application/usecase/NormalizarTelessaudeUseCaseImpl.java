package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.NormalizarTelessaudeUseCase;
import br.gov.ssd.datahub.application.port.out.AtendimentoNormalizadoRepositoryPort;
import br.gov.ssd.datahub.application.port.out.TelessaudeRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoNormalizado;
import br.gov.ssd.datahub.domain.model.FonteDados;
import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalizarTelessaudeUseCaseImpl implements NormalizarTelessaudeUseCase {

    private final TelessaudeRepositoryPort telessaudePort;
    private final AtendimentoNormalizadoRepositoryPort normalizadoPort;

    @Override
    public long executar() {
        List<RelatoCasoTelessaude> relatos = telessaudePort.buscarTodos();
        log.info("Telessaúde: normalizando {} registros", relatos.size());

        List<AtendimentoNormalizado> normalizados = relatos.stream()
                .map(this::normalizar)
                .toList();

        normalizadoPort.salvarTodos(normalizados);
        return normalizadoPort.contarPorFonte(FonteDados.TELESSAUDE);
    }

    private AtendimentoNormalizado normalizar(RelatoCasoTelessaude r) {
        Map<String, Object> extras = new HashMap<>();
        if (r.getQueixaPrincipal() != null)            extras.put("queixaPrincipal", r.getQueixaPrincipal());
        if (r.getLesaoFundamental() != null)           extras.put("lesaoFundamental", r.getLesaoFundamental());
        if (r.getCaracteristicasClinicas() != null)    extras.put("caracteristicasClinicas", r.getCaracteristicasClinicas());
        if (r.getHipotesePrincipal() != null)          extras.put("hipotesePrincipal", r.getHipotesePrincipal());
        if (r.getHipoteseSecundaria() != null)         extras.put("hipoteseSecundaria", r.getHipoteseSecundaria());
        if (r.getCondutaSugerida() != null)            extras.put("condutaSugerida", r.getCondutaSugerida());
        if (r.getRealizouBiopsia() != null)            extras.put("realizouBiopsia", r.getRealizouBiopsia());
        if (r.getLesaoSuspeita() != null)              extras.put("lesaoSuspeita", r.getLesaoSuspeita());
        if (r.getConsultorResponsavel() != null)       extras.put("consultorResponsavel", r.getConsultorResponsavel());
        if (r.getCorPele() != null)                    extras.put("corPele", r.getCorPele());

        return AtendimentoNormalizado.builder()
                .fonte(FonteDados.TELESSAUDE)
                .idOrigem(r.getRowId())
                .cpf(r.getCpf())
                .cns(r.getCns())
                .nomePaciente(r.getNomePaciente())
                .dataNascimento(r.getDataNascimento())
                .sexo(r.getSexo())
                .municipio(r.getMunicipio())
                .especialidade("Tele-estomatologia")
                .cid10Principal(r.getCid10())
                .cid10Secundario(null)
                .dataEvento(r.getDataRelato())
                .status(null)
                .tipoServico("Tele-estomatologia")
                .sourceFile(r.getSourceFile())
                .dadosExtras(extras)
                .build();
    }
}
