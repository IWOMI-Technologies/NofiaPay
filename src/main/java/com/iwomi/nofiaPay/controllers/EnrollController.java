package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.EnrollDto;
import com.iwomi.nofiaPay.dtos.responses.Enroll;
import com.iwomi.nofiaPay.dtos.responses.Transaction;
import com.iwomi.nofiaPay.services.enrollments.EnrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/enrolls")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class EnrollController {

    private  final EnrollService enrollService;

    @GetMapping()
    @Operation(
            description = "List of enroll users",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Enroll.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Enroll> result = enrollService.viewAllUsers();
       return GlobalResponse.responseBuilder("List of enroll users", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "User creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse"),
            }
    )
    public ResponseEntity<?> store(@RequestBody EnrollDto dto) {
       Enroll result = enrollService.saveUser(dto);
        return GlobalResponse.responseBuilder("User successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }
    @GetMapping("/{uuid}")
    @Operation(
            description = "Get a user",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Enroll.class))}),
            }
    )
    public ResponseEntity<?> show(@PathVariable UUID id) {
        Enroll result = enrollService.viewOne(id);
        return GlobalResponse.responseBuilder("Found User", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody EnrollDto dto) {
        Enroll result = enrollService.update(id, dto);
        return GlobalResponse.responseBuilder("User update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }
    @DeleteMapping("/{uuid}")
    @Operation(
            description = "Delete a user",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))}),
            }
    )
    public ResponseEntity<?> destroy(@PathVariable UUID uuid) {
     enrollService.deleteOne(uuid);
       return GlobalResponse.responseBuilder("User deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }

    @GetMapping("/{phone-number}")
    @Operation(
            description = "Get a user",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enroll.class))}),
            }
    )
    public ResponseEntity<?> showByPhoneNumber(@PathVariable String phoneNumber) {
        Enroll result = enrollService.viewByPhoneNumber(phoneNumber);
        return GlobalResponse.responseBuilder("Found User", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

}
