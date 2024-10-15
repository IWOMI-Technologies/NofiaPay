package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.enums.FileTypeEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.GeneralException;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.core.utils.CoreUtils;
import com.iwomi.nofiaPay.core.utils.DateConverterUtils;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.frameworks.data.entities.TransactionEntity;
import com.iwomi.nofiaPay.frameworks.data.repositories.transactions.TransactionRepository;
import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;
import com.iwomi.nofiaPay.processes.fileGeneration.GenerateTransaction;
import com.iwomi.nofiaPay.services.filesService.IFilesService;
import com.iwomi.nofiaPay.services.transactions.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("${apiV1Prefix}/files")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class FileController {
    private final IFilesService filesService;
    private final TransactionRepository transactionRepository;  // we use the repo here exceptionally.
    private final GenerateTransaction generateTransaction;

    /**
     * This is for client, accounts and account history files import in the DB
     */

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<?> importClients(@ModelAttribute UploadDto dto) {

        Boolean result = filesService.importFile(dto);

        return GlobalResponse.responseBuilder("File imported successfully", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    /**
     * This is for transactions file generation
     * In case we need to download it
     */
    @GetMapping("/download/excel")
    public ResponseEntity<?> downloadExcel(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        Date today = CoreUtils.localDateToDate(LocalDate.now());
        Date start = DateConverterUtils.stringToDate(startDate);
        Date end = DateConverterUtils.stringToDate(endDate);

        List<TransactionEntity> transactions = transactionRepository.getByCreatedAtBetween(start, end);
        List<UUID> uuids = transactions.stream().map(TransactionEntity::getUuid).toList();
        try {
            byte[] excelData = generateTransaction.toDownloadExcelTransactionFileGeneration(transactions);

            ByteArrayInputStream bis = new ByteArrayInputStream(excelData);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions_" + today + ".xlsx");

            // update all transactions to processed
            transactionRepository.markTransactionsProcessed(uuids);

            return new ResponseEntity<>(new InputStreamResource(bis), headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("error occurred: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/generate/transactions")
    public ResponseEntity<?> transactionsGeneration(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        Date today = CoreUtils.localDateToDate(LocalDate.now());
        Date start = DateConverterUtils.stringToDate(startDate);
        Date end = DateConverterUtils.stringToDate(endDate);

        List<TransactionEntity> transactions = transactionRepository.getByCreatedAtBetween(start, end);
        List<TransactionFile> results = generateTransaction.generate(transactions);

        return GlobalResponse.responseBuilder("Found generated transactions", HttpStatus.OK, HttpStatus.OK.value(), results);
    }
}
