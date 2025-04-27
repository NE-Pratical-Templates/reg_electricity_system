package rw.reg.Electricity.v1.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import rw.reg.Electricity.v1.validators.ValidAmount;

@Data
public class PurchaseDTO {
    @NotNull(message = "Meter number must not be null")
    @Pattern(regexp = "^\\d{6}$", message = "Meter number must be exactly 6 digits")
    private String meterNumber;

    @NotNull
    @ValidAmount
    private  Double amount;
}
