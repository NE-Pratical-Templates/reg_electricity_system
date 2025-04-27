package rw.reg.Electricity.v1.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import rw.reg.Electricity.v1.dtos.request.CreateUserDTO;
import rw.reg.Electricity.v1.dtos.request.LoginDTO;
import rw.reg.Electricity.v1.dtos.response.JwtAuthenticationResponse;
import rw.reg.Electricity.v1.models.User;

public interface IAuthService {
    User registerUser(@Valid CreateUserDTO dto);

    void verifyAccount(String verificationCode);

    void initiateAccountVerification(@Email String email);

    JwtAuthenticationResponse login(@Valid LoginDTO dto);

    void initiateResetPassword(@NotBlank @Email String email);

    void resetPassword(@NotBlank String email, @NotBlank String passwordResetCode, String newPassword);

    User getLoggedInCustomer();
}
