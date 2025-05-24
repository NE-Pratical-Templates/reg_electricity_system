package rw.reg.Electricity.v1.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.reg.Electricity.v1.dtos.response.TokenValidationResponse;
import rw.reg.Electricity.v1.models.Token;


public interface ITokenService {
    Page<Token> getAllTokens(Pageable pageable, String meterNumber);

    TokenValidationResponse validateToken(String dto);

    void expireTokens();
}
