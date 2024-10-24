package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.InquiryDto;
import com.iwomi.nofiaPay.dtos.ReplyDto;
import com.iwomi.nofiaPay.dtos.responses.Inquiry;
import com.iwomi.nofiaPay.dtos.responses.Reply;
import com.iwomi.nofiaPay.services.inquiries.InquiryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("${apiV1Prefix}/inquiries")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;
    @GetMapping()
    @Operation(
            description = "List of inquiries",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Inquiry.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Inquiry> result = inquiryService.viewAllInquiries();
        return GlobalResponse.responseBuilder("List of all inquiries", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "Inquiry creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> store(@RequestBody InquiryDto dto) {
        log.info("Received InquiryDto: {}", dto);
        Inquiry result = inquiryService.saveInquiry(dto);
        log.info("Saved Inquiry: {}", result);
        return GlobalResponse.responseBuilder("Inquiry successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/{uuid}/reply")
    @Operation(
            description = "Inquiry reply",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> reply(@PathVariable UUID uuid, @RequestBody ReplyDto dto) {
        Reply result = inquiryService.replyInquiry(uuid, dto);
        return GlobalResponse.responseBuilder("Inquiry replied successful", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> show(@PathVariable UUID uuid) {
        Inquiry result = inquiryService.viewOne(uuid);
        return GlobalResponse.responseBuilder("Inquiry found ", HttpStatus.OK, HttpStatus.OK.value(), result);
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID uuid, @RequestBody InquiryDto dto) {
        Inquiry result = inquiryService.updateInquiry(uuid, dto);
        return GlobalResponse.responseBuilder("Inquiry update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> destroy(@PathVariable UUID uuid) {
        inquiryService.deleteOne(uuid);
        return GlobalResponse.responseBuilder("Inquiry deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }
}
