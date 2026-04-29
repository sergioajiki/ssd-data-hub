package br.gov.ssd.datahub.infrastructure.adapter.in.rest;

import br.gov.ssd.datahub.application.port.in.NormalizarEinsteinUseCase;
import br.gov.ssd.datahub.application.port.in.NormalizarFiocruzUseCase;
import br.gov.ssd.datahub.application.port.in.NormalizarTelessaudeUseCase;
import br.gov.ssd.datahub.infrastructure.adapter.in.rest.dto.ResultadoNormalizacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/consolidacao")
@RequiredArgsConstructor
public class NormalizacaoController {

    private final NormalizarEinsteinUseCase normalizarEinstein;
    private final NormalizarFiocruzUseCase normalizarFiocruz;
    private final NormalizarTelessaudeUseCase normalizarTelessaude;

    @PostMapping("/normalizar")
    public ResponseEntity<ResultadoNormalizacaoDTO> normalizar() {
        log.info("Iniciando normalização de todas as fontes");

        long einstein    = normalizarEinstein.executar();
        long fiocruz     = normalizarFiocruz.executar();
        long telessaude  = normalizarTelessaude.executar();
        long total       = einstein + fiocruz + telessaude;

        log.info("Normalização concluída — Einstein: {}, Fiocruz: {}, Telessaúde: {}, Total: {}",
                einstein, fiocruz, telessaude, total);

        return ResponseEntity.ok(new ResultadoNormalizacaoDTO(einstein, fiocruz, telessaude, total));
    }
}
