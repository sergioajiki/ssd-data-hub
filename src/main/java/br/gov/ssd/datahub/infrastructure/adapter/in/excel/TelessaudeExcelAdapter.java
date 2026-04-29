package br.gov.ssd.datahub.infrastructure.adapter.in.excel;

import br.gov.ssd.datahub.application.port.in.ImportarTelessaudeUseCase;
import br.gov.ssd.datahub.domain.model.RelatoCasoTelessaude;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelessaudeExcelAdapter {

    private static final String ABA_RELATOS_CASO = "relatos_caso";
    private static final int LINHA_CABECALHO = 0;

    private static final DateTimeFormatter FMT_DATE_NORMALIZADA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final Map<String, String> MESES_PT = Map.ofEntries(
            Map.entry("jan.", "01"), Map.entry("fev.", "02"), Map.entry("mar.", "03"),
            Map.entry("abr.", "04"), Map.entry("mai.", "05"), Map.entry("jun.", "06"),
            Map.entry("jul.", "07"), Map.entry("ago.", "08"), Map.entry("set.", "09"),
            Map.entry("out.", "10"), Map.entry("nov.", "11"), Map.entry("dez.", "12")
    );

    private final ImportarTelessaudeUseCase useCase;

    public void processar(InputStream planilha) {
        List<RelatoCasoTelessaude> relatos = lerPlanilha(planilha);
        log.info("Telessaúde: {} relatos lidos da planilha", relatos.size());
        useCase.importar(relatos);
        log.info("Telessaúde: importação concluída");
    }

    private List<RelatoCasoTelessaude> lerPlanilha(InputStream planilha) {
        List<RelatoCasoTelessaude> resultado = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(planilha)) {
            Sheet sheet = workbook.getSheet(ABA_RELATOS_CASO);
            if (sheet == null) {
                throw new RuntimeException("Aba '" + ABA_RELATOS_CASO + "' não encontrada na planilha");
            }
            for (int i = LINHA_CABECALHO + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    resultado.add(mapearLinha(row));
                } catch (Exception e) {
                    log.warn("Telessaúde: erro ao processar linha {}: {}", i + 1, e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler planilha Telessaúde", e);
        }
        return resultado;
    }

    private RelatoCasoTelessaude mapearLinha(Row row) {
        return RelatoCasoTelessaude.builder()
                .emailProfissional(toString(cell(row, 0)))
                .motivoRelato(toString(cell(row, 1)))
                .nomeProfissional(toString(cell(row, 2)))
                .municipio(toString(cell(row, 3)))
                .localAtendimento(toString(cell(row, 4)))
                .dataRelato(toDatePtBr(cell(row, 5)))
                .nomePaciente(toString(cell(row, 6)))
                .cpf(toString(cell(row, 7)))
                .cns(toString(cell(row, 8)))
                .contatoPaciente(toString(cell(row, 9)))
                .idade(toInteger(cell(row, 10)))
                .dataNascimento(toDatePtBr(cell(row, 11)))
                .sexo(toString(cell(row, 12)))
                .corPele(toString(cell(row, 13)))
                .queixaPrincipal(toString(cell(row, 14)))
                .localizacaoAnatomica(toString(cell(row, 15)))
                .ladoAcometimento(toString(cell(row, 17)))
                .coloracao(toString(cell(row, 18)))
                .consistencia(toString(cell(row, 20)))
                .superficie(toString(cell(row, 22)))
                .insercao(toString(cell(row, 24)))
                .tamanho(toString(cell(row, 25)))
                .lesaoFundamental(toString(cell(row, 26)))
                .sintomatologia(toString(cell(row, 27)))
                .palpacaoLinfonodal(toString(cell(row, 28)))
                .caracteristicasClinicas(toString(cell(row, 30)))
                .tempoEvolucao(toString(cell(row, 36)))
                .historiaDoencaAtual(toString(cell(row, 37)))
                .doencaPreexistente(toString(cell(row, 38)))
                .habitosVicios(toString(cell(row, 40)))
                .hipotesePrincipal(toString(cell(row, 43)))
                .hipoteseSecundaria(toString(cell(row, 45)))
                .realizouBiopsia(toString(cell(row, 59)))
                .tipoBiopsia(toString(cell(row, 60)))
                .dataBiopsia(toDatePtBr(cell(row, 61)))
                .cid10(extrairCodigoCid(toString(cell(row, 71))))
                .hipotesePrincipalConsultor(toString(cell(row, 67)))
                .hipoteseSecundariaConsultor(toString(cell(row, 69)))
                .condutaSugerida(toString(cell(row, 73)))
                .coordenacaoCuidado(toString(cell(row, 74)))
                .qualidadeImagens(toString(cell(row, 76)))
                .lesaoSuspeita(toString(cell(row, 77)))
                .consultorResponsavel(toString(cell(row, 78)))
                .emailConsultor(toString(cell(row, 79)))
                .dataResposta(toDatePtBr(cell(row, 80)))
                .rowId(toString(cell(row, 81)))
                .sourceFile(toString(cell(row, 85)))
                .build();
    }

    private Cell cell(Row row, int index) {
        return row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
    }

    private String toString(Cell cell) {
        if (cell == null) return null;
        String value = switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) yield null;
                yield String.valueOf((long) cell.getNumericCellValue());
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
        if (value == null || value.isBlank() || value.equals("-")) return null;
        return value;
    }

    private Integer toInteger(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) return (int) cell.getNumericCellValue();
        try {
            String v = toString(cell);
            return v != null ? Integer.parseInt(v) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate toDatePtBr(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        String value = toString(cell);
        if (value == null) return null;
        try {
            String normalizada = value.toLowerCase();
            for (Map.Entry<String, String> entry : MESES_PT.entrySet()) {
                normalizada = normalizada.replace(entry.getKey(), entry.getValue());
            }
            return LocalDate.parse(normalizada, FMT_DATE_NORMALIZADA);
        } catch (Exception e) {
            return null;
        }
    }

    private String extrairCodigoCid(String cid) {
        if (cid == null) return null;
        int idx = cid.indexOf(" - ");
        return idx > 0 ? cid.substring(0, idx).trim() : cid.trim();
    }
}
