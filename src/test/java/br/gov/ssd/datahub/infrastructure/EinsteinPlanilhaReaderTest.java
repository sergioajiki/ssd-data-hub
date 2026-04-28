package br.gov.ssd.datahub.infrastructure;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

class EinsteinPlanilhaReaderTest {

    @Test
    void imprimirEstruturaDaPlanilha() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/einstein/raw_einstein.xlsx");
             Workbook workbook = new XSSFWorkbook(is)) {

            System.out.println("=== ESTRUTURA DA PLANILHA EINSTEIN ===\n");
            System.out.println("Total de abas: " + workbook.getNumberOfSheets());

            for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
                Sheet sheet = workbook.getSheetAt(s);
                System.out.println("\n--- Aba " + (s + 1) + ": " + sheet.getSheetName() + " ---");
                System.out.println("Total de linhas: " + sheet.getPhysicalNumberOfRows());

                Row header = sheet.getRow(0);
                if (header == null) {
                    System.out.println("(aba sem cabeçalho)");
                    continue;
                }

                System.out.println("Colunas:");
                for (int c = 0; c < header.getLastCellNum(); c++) {
                    Cell cell = header.getCell(c);
                    String colName = (cell != null) ? cell.toString() : "(vazio)";

                    Row firstData = sheet.getRow(1);
                    String exemplo = "";
                    if (firstData != null) {
                        Cell dataCell = firstData.getCell(c);
                        exemplo = (dataCell != null) ? " | ex: " + dataCell.toString() : "";
                    }

                    System.out.printf("  [%02d] %s%s%n", c + 1, colName, exemplo);
                }
            }
        }
    }
}
