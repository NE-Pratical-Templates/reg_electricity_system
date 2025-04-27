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
import rw.reg.Electricity.v1.interfaces.IUserService;
import rw.reg.Electricity.v1.utils.Constants;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    //     get all  users by admin
    @Operation(
            summary = "get all  users by admin",
            description = "get all  user by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " all  user by admin  fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    private ResponseEntity<ApiResponseDTO> getAllUsersByAdmin(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", userService.getAllUsers(pageable)));
    }

    //     get   user by id
    @Operation(
            summary = "User fetched successfully",
            description = "User fetched successfully",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "  User fetched successfully successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token")
    })
    @GetMapping("/user/{id}")
    private ResponseEntity<ApiResponseDTO> getUserByIdByAdmin(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponseDTO.success("User fetched successfully", userService.getUserByID(id)));
    }

    //    search   user by admin
    @Operation(
            summary = " search   user by admin ",
            description = " search   user by admin",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/search")
    public ResponseEntity<ApiResponseDTO> search(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "q") String q
    ) {
        Pageable pageable = Pageable.ofSize(limit).withPage(page);
        return ResponseEntity.ok(ApiResponseDTO.success("Users fetched successfully", this.userService.search(pageable, q)));
    }
}
