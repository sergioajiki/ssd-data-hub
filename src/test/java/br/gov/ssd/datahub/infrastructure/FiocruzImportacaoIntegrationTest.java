package br.gov.ssd.datahub.infrastructure;

import br.gov.ssd.datahub.infrastructure.adapter.in.excel.FiocruzExcelAdapter;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.FiocruzJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FiocruzImportacaoIntegrationTest {

    @Autowired
    private FiocruzExcelAdapter excelAdapter;

    @Autowired
    private FiocruzJpaRepository repository;

    @Test
    void deveImportarPlanilhaFiocruzEPersistirNoBanco() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/fiocruz/raw_fiocruz.xlsx")) {
            assertThat(is).isNotNull();
            excelAdapter.processar(is);
        }

        long totalPrimeiraImportacao = repository.count();
        System.out.println("Registros após 1ª importação: " + totalPrimeiraImportacao);
        assertThat(totalPrimeiraImportacao).isGreaterThan(0);

        try (InputStream is = getClass().getResourceAsStream("/fiocruz/raw_fiocruz.xlsx")) {
            excelAdapter.processar(is);
        }

        long totalSegundaImportacao = repository.count();
        System.out.println("Registros após 2ª importação (deve ser igual): " + totalSegundaImportacao);
        assertThat(totalSegundaImportacao).isEqualTo(totalPrimeiraImportacao);
    }
}
