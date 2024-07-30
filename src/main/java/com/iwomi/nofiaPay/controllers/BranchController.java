//package com.iwomi.nofiaPay.controllers;
//
//import com.iwomi.nofiaPay.services.branches.BranchService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/api/v1/branches")
//@RequiredArgsConstructor
//@CrossOrigin("*")
//@RestController
//public class BranchController {
//    private final BranchService branchService;
//
//    @GetMapping()
////    @Operation(
////            description = "List of accounts",
////            responses = {
////                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
////                    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
////                            schema = @Schema(implementation = Account.class))}),
////            }
////    )
//    public ResponseEntity<?> index() {
//        List<Account> result = accountUsecases.viewAllAccounts();
//        return GlobalResponse.responseBuilder("List of accounts", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }
//
//    @PostMapping()
//    @Operation(
//            description = "Account creation",
//            responses = {
//                    @ApiResponse(responseCode = "400", ref = "badRequest"),
//                    @ApiResponse(responseCode = "500", ref = "internalServerErrorApi"),
//                    @ApiResponse(responseCode = "201", ref = "successResponse"),
//            }
//    )
//    public ResponseEntity<?> store(@Valid @RequestBody AccountDto dto) {
//        Account result = accountUsecases.saveAccount(dto);
//        return GlobalResponse.responseBuilder("Account successfully created", HttpStatus.CREATED, HttpStatus.CREATED.value(), result);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> show(@PathVariable UUID id) {
//        Account result = accountUsecases.viewOne(id);
//        return GlobalResponse.responseBuilder("Found account", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody AccountDto dto) {
//        Account result = accountUsecases.update(id, dto);
//        return GlobalResponse.responseBuilder("Account update successful", HttpStatus.OK, HttpStatus.OK.value(), result);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> destroy(@PathVariable UUID id) {
//        accountUsecases.deleteOne(id);
//        return GlobalResponse.responseBuilder("Account deleted", HttpStatus.OK, HttpStatus.OK.value(), null);
//    }
//}
