package rw.reg.Electricity.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.reg.Electricity.v1.dtos.request.CreateMeterDTO;
import rw.reg.Electricity.v1.dtos.response.ApiResponseDTO;
import rw.reg.Electricity.v1.exceptions.BadRequestException;
import rw.reg.Electricity.v1.interfaces.IAuthService;
import rw.reg.Electricity.v1.interfaces.IMeterService;
import rw.reg.Electricity.v1.models.User;
import rw.reg.Electricity.v1.utils.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meters")
@RequiredArgsConstructor
public class MeterController {
    private final IMeterService meterService;
    private final IAuthService authService;

    @Operation(summary = "Register a new meter"
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/{customerID}")
    ResponseEntity<ApiResponseDTO> registerMeter(@Valid @RequestBody CreateMeterDTO dto, @PathVariable(required = true) UUID customerID
    ) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDTO.success("meter created successfully", meterService.createMeter(dto, customerID)));
    }

    //     get all  meter numbers by admin
    @Operation(
            summary = "get all  meter numbers by admin",
            description = "get all  meter numbers by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  meter numbers by admin  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private ResponseEntity<ApiResponseDTO> getAllMeterNumbersByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("meter numbers fetched successfully", meterService.getAllMeterNumbers(pageable)));
    }


    //     get all  meter numbers of customer by admin
    @Operation(
            summary = "get all  meter numbers of customer by admin",
            description = "get all  meter numbers of customer by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  meter numbers of customer  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/customer/all/{customerID}")
    private ResponseEntity<ApiResponseDTO> getAllMeterNumbersByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit, @PathVariable(required = true) UUID customerID) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("meter numbers fetched successfully", meterService.getAllMeterNumbersByCustomer(pageable, customerID)));
    }

    //     get all  meter numbers of logged in customer
    @Operation(
            summary = "get all  meter numbers of logged in customer",
            description = "get all  meter numbers of logged in customer",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  meter numbers of customer  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @GetMapping("/customer")
    private ResponseEntity<ApiResponseDTO> getAllMeterNumbersLoggedCustomer(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        User loggedInUser = authService.getLoggedInCustomer();
        return ResponseEntity.ok(ApiResponseDTO.success("meter numbers fetched successfully", meterService.getAllMeterNumbersByCustomer(pageable, loggedInUser.getId())));
    }


    //    search   meter number
    @Operation(
            summary = " search   meter number  ",
            description = " search   meter number ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(path = "/search")
    public ResponseEntity<ApiResponseDTO> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "q") String q
    ) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", this.meterService.search(pageable, q)));
    }
}
