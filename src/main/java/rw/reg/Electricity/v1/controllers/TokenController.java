package rw.reg.Electricity.v1.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.reg.Electricity.v1.dtos.response.ApiResponseDTO;
import rw.reg.Electricity.v1.exceptions.BadRequestException;
import rw.reg.Electricity.v1.interfaces.IMessageService;
import rw.reg.Electricity.v1.interfaces.ITokenService;
import rw.reg.Electricity.v1.utils.Constants;


@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {
    private  final ITokenService tokenService;
    private  final IMessageService messageService;

    //     get all  tokens related to  meter number
    @Operation(
            summary = "get all  tokens related to  meter number ",
            description = "get all  tokens related to  meter number ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  tokens related to  meter number  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @GetMapping("/all/{meterNumber}")
    private ResponseEntity<ApiResponseDTO> getAllTokensByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit, @PathVariable(required = true) String meterNumber) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("tokens  fetched successfully", tokenService.getAllTokens(pageable, meterNumber)));
    }

    //     validate token
    @Operation(
            summary = "validate token",
            description = "validate token",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " token  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @GetMapping("/validate/{token}")
    private ResponseEntity<ApiResponseDTO> validateToken( @PathVariable(required = true) String token) {
        return ResponseEntity.ok(ApiResponseDTO.success("tokens  validatedSuccessfully fetched successfully", tokenService.validateToken(token)));
    }

    //notify customer that token is expiring
    @Operation(summary = "notify customer that token is expiring"
            , security = @SecurityRequirement(name = "bearerAuth")

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "notify customer that token is expiring"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notify/{token}")
    ResponseEntity<ApiResponseDTO> registerMeter( @PathVariable(required = true) String token
    ) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDTO.success("notify customer that token is expiring successfully", messageService.notifyCustomer( token)));
    }
}
