package rw.reg.Electricity.v1.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import rw.reg.Electricity.v1.validators.ValidPassword;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateUserDTO {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private String mobile;

    @NotNull
    @PastOrPresent(message = "Date of birth should be in the past")
    @NotNull(message = "Date of birth should not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Pattern(regexp = "^1\\d{15}$", message = "provide valid national ID")
    private String nationalId;
}
