package rw.reg.Electricity.v1.dtos.request;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;
import rw.reg.Electricity.v1.validators.ValidPassword;

@Getter
public class ResetPasswordDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String passwordResetCode;

    @ValidPassword
    private String newPassword;
}
