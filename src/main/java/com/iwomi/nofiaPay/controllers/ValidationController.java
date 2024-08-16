package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.BranchDto;
import com.iwomi.nofiaPay.dtos.responses.Branch;
import com.iwomi.nofiaPay.dtos.responses.Enroll;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.SubscriptionValidationEntity;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import com.iwomi.nofiaPay.services.validations.IvalidationService;
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
import java.util.Map;

@RequestMapping("${apiV1Prefix}/validations")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class ValidationController {
    private final IvalidationService validationService;

    @GetMapping()
    @Operation(
            description = "List of clients by user type (in auth ms) and validation status",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Enroll.class))}),
            }
    )
    public ResponseEntity<?> showClientByTypeAndStatus(
//            @RequestParam("profile") String profile,
            @RequestParam("role") UserTypeEnum role,
            @RequestParam("status") ValidationStatusEnum status
    ) {
        List<ClientEntity> data = validationService.viewByStatus(role, status);
        Boolean canValidate = validationService.canValidate(role.toString());
        Map<String, Object> result = Map.of("data", data, "canValidate", canValidate);
        return GlobalResponse.responseBuilder("List of enroll users", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "souscription validation creation NB: meant only for another ms",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionValidationEntity.class))}),
            }
    )
    public ResponseEntity<?> store(@RequestParam String clientCode) {
        SubscriptionValidationEntity result = validationService.sendToValidation(clientCode);
        return GlobalResponse.responseBuilder("Added successfully to validation", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/validate")
    @Operation(
            description = "perform subscription validation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionValidationEntity.class))}),
            }
    )
    public ResponseEntity<?> validate(@RequestParam String clientCode, @RequestParam String userId) {
        SubscriptionValidationEntity result = validationService.validate(clientCode, userId);
        return GlobalResponse.responseBuilder("Validation successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

}
