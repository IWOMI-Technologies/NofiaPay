package com.iwomi.nofiaPay.processes.fileGeneration;

import com.iwomi.nofiaPay.core.enums.OperationTypeEnum;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.core.utils.FileStorageUtil;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class GenerateTransaction {
    private final Path storagePath;
    private final TransactionRepository repository;
    private final IGeneration generation;

    public GenerateTransaction(Environment env, TransactionRepository repository, IGeneration generation) {
        this.storagePath = Paths.get(env.getProperty("app.files.downloads", "uploads/files"))
                .toAbsolutePath().normalize();
        this.repository = repository;
        this.generation = generation;
    }

    public List<TransactionFile> generate() {
        Date today = CoreUtils.localDateToDate(LocalDate.now());
        List<TransactionEntity> result = repository.getTodayTransactions(today);

        return result.stream()
                .flatMap(entity -> switch (entity.getType()) {
                    case AGENT_CASH_COLLECTION -> generation.agentCashCollection(entity).stream();
                    case AGENT_DIGITAL_COLLECTION_OM, AGENT_DIGITAL_COLLECTION_MOMO ->
                            generation.agentDigitalCollection(entity).stream();
                    case SELF_SERVICE_OM, SELF_SERVICE_MOMO -> generation.selfService(entity).stream();
                    default -> generation.merchantDigitalCollection(entity).stream();   // MERCHANT OM and MOMO
                })
                .toList();
    }

    public void excelTransactionFileGeneration() {
        List<TransactionFile> transactions = generate();
        Path path = FileStorageUtil.createSubFolder(storagePath, "downloads");
        String fileExtension = ".xlsx";
        String fileName = new Date().getTime() + fileExtension;
        String filePath = path.toUri() + "/" + fileName;

        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("Transactions");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Account Number", "Transaction Label", "Transaction Reference", "Debited Amount", "Credited Amount", "Reference Lettering" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(getHeaderCellStyle(workbook));
            }

            // Populate data rows
            int rowNum = 1;
            for (TransactionFile transaction : transactions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaction.accountNumber());
                row.createCell(1).setCellValue(transaction.transactionLabel());
                row.createCell(2).setCellValue(transaction.transactionReference());
                row.createCell(3).setCellValue(transaction.debitedAmount());
                row.createCell(4).setCellValue(transaction.creditedAmount());
                row.createCell(5).setCellValue(transaction.referenceLettering());
            }

            // Adjust column widths
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}
