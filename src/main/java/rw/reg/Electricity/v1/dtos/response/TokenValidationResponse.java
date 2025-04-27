package rw.reg.Electricity.v1.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.reg.Electricity.v1.enums.ETokenStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {
    private String formattedToken;
    private int tokenValueDays;
    private ETokenStatus tokenStatus;
}
