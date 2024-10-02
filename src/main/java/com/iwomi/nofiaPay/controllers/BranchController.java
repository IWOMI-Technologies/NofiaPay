package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.services.branches.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("${apiV1Prefix}/branches")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class BranchController {
    private final BranchService branchService;

    @GetMapping()
    @Operation(
            description = "List of branches",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Branch.class))}),
            }
    )
    public ResponseEntity<?> index() {
        List<Branch> result = branchService.findAllBranches();
        return GlobalResponse.responseBuilder("List of branches", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "Branch creation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Branch.class))}),
            }
    )
    public ResponseEntity<?> store(@Valid @RequestBody BranchDto dto) {
        Branch result = branchService.saveBranch(dto);
        return GlobalResponse.responseBuilder("Branch successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get a branch",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Branch.class))}),
            }
    )
    public ResponseEntity<?> show(@PathVariable UUID id) {
        Branch result = branchService.viewOne(id);
        return GlobalResponse.responseBuilder("Found branch", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody BranchDto dto) {
        Branch result = branchService.update(id, dto);
        return GlobalResponse.responseBuilder("Branch update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete a branch",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))}),
            }
    )
    public ResponseEntity<?> destroy(@PathVariable UUID id) {
        branchService.deleteOne(id);
        return GlobalResponse.responseBuilder("Branch deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
    }
}
