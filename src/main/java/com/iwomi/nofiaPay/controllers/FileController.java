package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.UploadDto;
import com.iwomi.nofiaPay.services.filesService.IFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("${apiV1Prefix}/files")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class FileController {
    private final IFilesService filesService;

    @PostMapping(value = "/import", consumes = {"multipart/form-data"})
    public ResponseEntity<?> importClients(@ModelAttribute UploadDto dto) {

        Boolean result = filesService.importFile(dto);

        return GlobalResponse.responseBuilder("File imported successfully", HttpStatus.OK, HttpStatus.OK.value(), result);
    }
}
