package br.gov.ssd.datahub.infrastructure.adapter.in.excel;

import br.gov.ssd.datahub.application.port.in.ImportarFiocruzUseCase;
import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FiocruzExcelAdapter {

    private static final int LINHA_CABECALHO = 0;

    private static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_DATE_NORMALIZADA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter FMT_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final Map<String, String> MESES_PT = Map.ofEntries(
            Map.entry("jan.", "01"), Map.entry("fev.", "02"), Map.entry("mar.", "03"),
            Map.entry("abr.", "04"), Map.entry("mai.", "05"), Map.entry("jun.", "06"),
            Map.entry("jul.", "07"), Map.entry("ago.", "08"), Map.entry("set.", "09"),
            Map.entry("out.", "10"), Map.entry("nov.", "11"), Map.entry("dez.", "12")
    );

    private final ImportarFiocruzUseCase useCase;

    public void processar(InputStream planilha) {
        List<AtendimentoFiocruz> atendimentos = lerPlanilha(planilha);
        log.info("Fiocruz: {} registros lidos da planilha", atendimentos.size());
        useCase.importar(atendimentos);
        log.info("Fiocruz: importação concluída");
    }

    private List<AtendimentoFiocruz> lerPlanilha(InputStream planilha) {
        List<AtendimentoFiocruz> resultado = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(planilha)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = LINHA_CABECALHO + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    resultado.add(mapearLinha(row));
                } catch (Exception e) {
                    log.warn("Fiocruz: erro ao processar linha {}: {}", i + 1, e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler planilha Fiocruz", e);
        }
        return resultado;
    }

    private AtendimentoFiocruz mapearLinha(Row row) {
        return AtendimentoFiocruz.builder()
                .dataSolicitacao(toDatePtBr(cell(row, 0)))
                .tipoServico(toString(cell(row, 1)))
                .dataAgendamento(toDate(cell(row, 2)))
                .horaAtendimento(toTime(cell(row, 3)))
                .estabelecimento(toString(cell(row, 4)))
                .especialidade(toString(cell(row, 5)))
                .especialidadeMedico(toString(cell(row, 6)))
                .cpfMedico(toCpf(cell(row, 7)))
                .cboMedico(toString(cell(row, 8)))
                .statusConsulta(toString(cell(row, 9)))
                .municipio(toString(cell(row, 10)))
                .cpfPaciente(toString(cell(row, 11)))
                .nomePaciente(toString(cell(row, 12)))
                .cnsPaciente(toString(cell(row, 13)))
                .racaPaciente(toString(cell(row, 14)))
                .etniaPaciente(toString(cell(row, 15)))
                .dataNascimento(toDate(cell(row, 16)))
                .cidConsulta(toString(cell(row, 17)))
                .telefone(toString(cell(row, 18)))
                .tipoZona(toString(cell(row, 19)))
                .enderecoCompleto(toString(cell(row, 20)))
                .rua(toString(cell(row, 21)))
                .cep(toString(cell(row, 22)))
                .numImovel(toString(cell(row, 23)))
                .bairro(toString(cell(row, 24)))
                .complemento(toString(cell(row, 25)))
                .classificacaoConclusao(toString(cell(row, 26)))
                .descricaoEndereco(toString(cell(row, 27)))
                .sourceFile(toString(cell(row, 28)))
                .build();
    }

    private Cell cell(Row row, int index) {
        return row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
    }

    private String toString(Cell cell) {
        if (cell == null) return null;
        String value = switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
        if (value == null || value.isBlank() || value.equals("-") || value.equals(", ")) return null;
        return value;
    }

    private LocalDate toDate(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }
        String value = toString(cell);
        if (value == null) return null;
        try {
            return LocalDate.parse(value, FMT_DATE);
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

    private LocalTime toTime(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getLocalDateTimeCellValue().toLocalTime();
        }
        String value = toString(cell);
        if (value == null) return null;
        try {
            return LocalTime.parse(value, FMT_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    private String toCpf(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            long cpfLong = (long) cell.getNumericCellValue();
            return String.format("%011d", cpfLong);
        }
        String value = toString(cell);
        if (value == null) return null;
        try {
            long cpfLong = (long) Double.parseDouble(value);
            return String.format("%011d", cpfLong);
        } catch (Exception e) {
            return value;
        }
    }
}
