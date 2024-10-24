package com.iwomi.nofiaPay.controllers;

import com.iwomi.nofiaPay.core.enums.StatusTypeEnum;
import com.iwomi.nofiaPay.core.enums.ValidationStatusEnum;
import com.iwomi.nofiaPay.core.errors.exceptions.UnAuthorizedException;
import com.iwomi.nofiaPay.core.response.GlobalResponse;
import com.iwomi.nofiaPay.dtos.responses.Enroll;
import com.iwomi.nofiaPay.frameworks.data.entities.ClientEntity;
import com.iwomi.nofiaPay.frameworks.data.entities.ValidationEntity;
import com.iwomi.nofiaPay.frameworks.externals.enums.UserTypeEnum;
import com.iwomi.nofiaPay.services.validations.IvalidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("${apiV1Prefix}/validations")
@RequiredArgsConstructor
//@CrossOrigin("*")
@RestController
public class ValidationController {
    private final IvalidationService validationService;

    @GetMapping()
    @Operation(
            description = "List of clients by user type (in auth ms) and validation status. check canValidate for val btn display",
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
//            @RequestParam("type")ValidationTypeEnum type
    ) {
        List<ClientEntity> data = validationService.viewByStatus(role, status);
        System.out.println("data: " + data);
        Boolean canValidate = validationService.canValidate(role.toString());
        if (!canValidate) throw new UnAuthorizedException("You are not permitted to validate this role");
//        System.out.println("canValidate: " + canValidate);
//        Map<String, Object> result = Map.of("data", data, "canValidate", canValidate);

        return GlobalResponse.responseBuilder("List of pending validations", HttpStatus.OK, HttpStatus.OK.value(), data);
    }

    @GetMapping("/awaiting")
    @Operation(
            description = "List of reversement validation requests for a teller",
            responses = {
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "successResponse",
                            content = {@Content(schema = @Schema(implementation = Enroll.class))}),
            }
    )
    public ResponseEntity<?> showRerversementValidation(
            @RequestParam("tellerClientCode") String tellerClientCode
    ) {
        Map<String, Object> result = validationService.tellerValidationRequests(tellerClientCode);
        return GlobalResponse.responseBuilder("List of reversement validation requests", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping()
    @Operation(
            description = "souscription validation creation NB: meant only for another ms",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationEntity.class))}),
            }
    )
    public ResponseEntity<?> store(@RequestParam String clientCode) {
        ValidationEntity result = validationService.sendToSubscriptionValidation(clientCode);
        return GlobalResponse.responseBuilder("Added successfully to validation", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
    }

    @PostMapping("/validate")
    @Operation(
            description = "perform subscription validation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationEntity.class))}),
            }
    )
    public ResponseEntity<?> validateSubscription(
            @RequestParam String clientCode,
            @RequestParam String userId,
            @RequestParam ValidationStatusEnum validationStatus
    ) {
        ValidationEntity result = validationService.validate(clientCode, userId, validationStatus);
//        if (validationStatus == ValidationStatusEnum.VALIDATED)
//            websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());   // to teller
//        else websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());
        return GlobalResponse.responseBuilder("Validation successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

    @PostMapping("/validate/transfer")
    @Operation(
            description = "perform transfer validation",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequest"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
                    @ApiResponse(responseCode = "201", ref = "createdResponse", content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationEntity.class))}),
            }
    )
    public ResponseEntity<?> validateTransfer(
            @RequestParam String clientCode,
            @RequestParam String userId,
            @RequestParam ValidationStatusEnum validationStatus
    ) {
        ValidationEntity result = validationService.validateTransfer(clientCode, userId, validationStatus);
//        if (validationStatus == ValidationStatusEnum.VALIDATED)
//            websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());   // to teller
//        else websocketService.sendToUser(userid, StatusTypeEnum.VALIDATED.toString());
        return GlobalResponse.responseBuilder("Validation successful", HttpStatus.OK, HttpStatus.OK.value(), result);
    }

}
