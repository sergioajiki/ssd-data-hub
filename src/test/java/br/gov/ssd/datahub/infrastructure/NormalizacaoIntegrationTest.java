package br.gov.ssd.datahub.infrastructure;

import br.gov.ssd.datahub.application.port.in.NormalizarEinsteinUseCase;
import br.gov.ssd.datahub.application.port.in.NormalizarFiocruzUseCase;
import br.gov.ssd.datahub.application.port.in.NormalizarTelessaudeUseCase;
import br.gov.ssd.datahub.infrastructure.adapter.in.excel.EinsteinExcelAdapter;
import br.gov.ssd.datahub.infrastructure.adapter.in.excel.FiocruzExcelAdapter;
import br.gov.ssd.datahub.infrastructure.adapter.in.excel.TelessaudeExcelAdapter;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.AtendimentoNormalizadoJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NormalizacaoIntegrationTest {

    @Autowired private EinsteinExcelAdapter einsteinAdapter;
    @Autowired private FiocruzExcelAdapter fiocruzAdapter;
    @Autowired private TelessaudeExcelAdapter telessaudeAdapter;

    @Autowired private NormalizarEinsteinUseCase normalizarEinstein;
    @Autowired private NormalizarFiocruzUseCase normalizarFiocruz;
    @Autowired private NormalizarTelessaudeUseCase normalizarTelessaude;

    @Autowired private AtendimentoNormalizadoJpaRepository normalizadoRepository;

    @Test
    void deveImportarNormalizarEDeduplicar() throws Exception {
        // Importa as três fontes
        try (InputStream is = getClass().getResourceAsStream("/einstein/raw_einstein.xlsx")) {
            einsteinAdapter.processar(is);
        }
        try (InputStream is = getClass().getResourceAsStream("/fiocruz/raw_fiocruz.xlsx")) {
            fiocruzAdapter.processar(is);
        }
        try (InputStream is = getClass().getResourceAsStream("/telessaude/raw_teleestomato.xlsx")) {
            telessaudeAdapter.processar(is);
        }

        // Normaliza
        long einstein   = normalizarEinstein.executar();
        long fiocruz    = normalizarFiocruz.executar();
        long telessaude = normalizarTelessaude.executar();

        System.out.println("Normalizado — Einstein: " + einstein +
                ", Fiocruz: " + fiocruz + ", Telessaúde: " + telessaude);

        assertThat(einstein).isGreaterThan(0);
        assertThat(fiocruz).isGreaterThan(0);
        assertThat(telessaude).isGreaterThan(0);

        long totalPrimeiraRodada = normalizadoRepository.count();

        // Normaliza novamente — total não deve crescer
        normalizarEinstein.executar();
        normalizarFiocruz.executar();
        normalizarTelessaude.executar();

        long totalSegundaRodada = normalizadoRepository.count();
        System.out.println("Total após 2ª normalização (deve ser igual): " + totalSegundaRodada);

        assertThat(totalSegundaRodada).isEqualTo(totalPrimeiraRodada);
    }
}
