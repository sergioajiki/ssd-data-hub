package br.gov.ssd.datahub.infrastructure.adapter.in.excel;

import br.gov.ssd.datahub.application.port.in.ImportarEinsteinUseCase;
import br.gov.ssd.datahub.domain.model.SolicitacaoEinstein;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EinsteinExcelAdapter {

    private static final int LINHA_CABECALHO = 0;
    private static final DateTimeFormatter FMT_DATETIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FMT_DATETIME_SHORT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FMT_DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_DATETIME_FLEX = new DateTimeFormatterBuilder()
            .appendOptional(FMT_DATETIME)
            .appendOptional(FMT_DATETIME_SHORT)
            .toFormatter();

    private final ImportarEinsteinUseCase useCase;

    public void processar(InputStream planilha) {
        List<SolicitacaoEinstein> solicitacoes = lerPlanilha(planilha);
        log.info("Einstein: {} registros lidos da planilha", solicitacoes.size());
        useCase.importar(solicitacoes);
        log.info("Einstein: importação concluída");
    }

    private List<SolicitacaoEinstein> lerPlanilha(InputStream planilha) {
        List<SolicitacaoEinstein> resultado = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(planilha)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = LINHA_CABECALHO + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    resultado.add(mapearLinha(row));
                } catch (Exception e) {
                    log.warn("Einstein: erro ao processar linha {}: {}", i + 1, e.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler planilha Einstein", e);
        }
        return resultado;
    }

    private SolicitacaoEinstein mapearLinha(Row row) {
        return SolicitacaoEinstein.builder()
                .idSolicitacao(toLong(cell(row, 0)))
                .status(toString(cell(row, 1)))
                .telesolicitante(toString(cell(row, 2)))
                .teleconsultor(toString(cell(row, 3)))
                .finalizador(toString(cell(row, 4)))
                .teleregulador(toString(cell(row, 5)))
                .horaSolicitado(toDateTime(cell(row, 6)))
                .horaConsultado(toDateTime(cell(row, 7)))
                .horaFinalizado(toDateTime(cell(row, 8)))
                .horaAuditado(toDateTime(cell(row, 9)))
                .horaModificado(toDateTime(cell(row, 10)))
                .agendamentoConfirmado(toDateTime(cell(row, 11)))
                .agendamentoCancelado(toDateTime(cell(row, 12)))
                .nomePaciente(toString(cell(row, 13)))
                .paisNascimento(toString(cell(row, 14)))
                .cns(normalizarCns(toString(cell(row, 15))))
                .cpf(toString(cell(row, 16)))
                .dataNascimento(toDate(cell(row, 17)))
                .idade(toInteger(cell(row, 18)))
                .idadeEmDias(toInteger(cell(row, 19)))
                .sexo(toString(cell(row, 20)))
                .nomeMae(toString(cell(row, 21)))
                .especialidadeSolicitada(toString(cell(row, 22)))
                .nomeMedicoTelesolicitante(toString(cell(row, 23)))
                .nomeMedicoSolicitante(toString(cell(row, 24)))
                .crmMedicoSolicitante(toString(cell(row, 25)))
                .medicamentosEmUso(toString(cell(row, 26)))
                .historiaClinica(toString(cell(row, 27)))
                .cid10Principal(toString(cell(row, 28)))
                .cid10Secundario(toString(cell(row, 29)))
                .recomendacoesMedicas(toString(cell(row, 30)))
                .resumoClinico(toString(cell(row, 31)))
                .desfecho(toString(cell(row, 32)))
                .sourceFile(toString(cell(row, 33)))
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
        if (value == null || value.isBlank() || value.equals("-")) return null;
        return value;
    }

    private Long toLong(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        }
        try {
            return Long.parseLong(toString(cell));
        } catch (Exception e) {
            return null;
        }
    }

    private Integer toInteger(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        }
        try {
            return Integer.parseInt(toString(cell));
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime toDateTime(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue();
        }
        String value = toString(cell);
        if (value == null) return null;
        try {
            return LocalDateTime.parse(value, FMT_DATETIME_FLEX);
        } catch (Exception e) {
            return null;
        }
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

    private String normalizarCns(String cns) {
        if (cns == null) return null;
        return cns.replaceAll("\\s+", "");
    }
}
