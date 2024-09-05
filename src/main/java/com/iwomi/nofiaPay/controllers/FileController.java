package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.frameworks.generate.TransactionFile;
import com.iwomi.nofiaPay.services.filesService.IFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

@RequestMapping("${apiV1Prefix}/files")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class FileController {
    private final IFilesService filesService;

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
//    @GetMapping("/download/excel")
//    public ResponseEntity<byte[]> downloadExcel(@RequestParam(value = "example", defaultValue = "true") boolean example) {
//        try {
//            List<TransactionFile> transactions;
//            if (example) {
//                transactions = Arrays.asList(
//                        new TransactionFile("12345", "Payment", "TRX001", "100.00", "", "Invoice 001"),
//                        new TransactionFile("67890", "Refund", "TRX002", "", "50.00", "Invoice 002")
//                );
//            } else {
//                transactions = fetchDataFromSomewhere(); // Replace with actual data source
//            }
//
//            ByteArrayInputStream in = excelService.generateExcel(transactions);
//            byte[] bytes = in.readAllBytes();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.xlsx");
//            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
