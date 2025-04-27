package rw.reg.Electricity.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.reg.Electricity.v1.dtos.request.PurchaseDTO;
import rw.reg.Electricity.v1.dtos.response.ApiResponseDTO;
import rw.reg.Electricity.v1.exceptions.BadRequestException;
import rw.reg.Electricity.v1.interfaces.IAuthService;
import rw.reg.Electricity.v1.interfaces.IPurchaseService;
import rw.reg.Electricity.v1.models.User;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {
    private final IPurchaseService purchaseService;
    private  final IAuthService authService;

    @Operation(summary = "purchase token "
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "token generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    ResponseEntity<ApiResponseDTO> registerMeter(@Valid @RequestBody PurchaseDTO dto
    ) throws BadRequestException {
        User customer = authService.getLoggedInCustomer();
        return ResponseEntity.ok(ApiResponseDTO.success("token generated created successfully", purchaseService.purchaseToken(dto, customer.getId())));
    }
}
