package rw.reg.Electricity.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rw.reg.Electricity.v1.dtos.response.TokenValidationResponse;
import rw.reg.Electricity.v1.enums.ETokenStatus;
import rw.reg.Electricity.v1.exceptions.BadRequestException;
import rw.reg.Electricity.v1.exceptions.ResourceNotFoundException;
import rw.reg.Electricity.v1.interfaces.ITokenService;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.Token;
import rw.reg.Electricity.v1.repositories.IMeterRepository;
import rw.reg.Electricity.v1.repositories.ITokenRepository;
import rw.reg.Electricity.v1.utils.Utility;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements ITokenService {
    private final ITokenRepository tokenRepo;
    private final IMeterRepository meterRepo;

    @Override
    public Page<Token> getAllTokens(Pageable pageable, String meterNumber) {
        MeterNumber meter = meterRepo.findByMeterNumber(meterNumber).orElseThrow(() -> new ResourceNotFoundException(" no meter found with that number ", "meter_number", meterNumber));
        return tokenRepo.findByMeterNumber(meter, pageable);
    }

    @Override
    public TokenValidationResponse validateToken(String dto) {
        Token token = tokenRepo.findByToken(dto).orElseThrow(() -> new ResourceNotFoundException(" no token found , please enter valid token  ", "token", dto));
        if (token.getStatus() == ETokenStatus.USED) {
            throw new BadRequestException("token has been already used");
        } else if (token.getStatus() == ETokenStatus.EXPIRED) {
            throw new BadRequestException("token has already expired");
        }
        return new TokenValidationResponse(Utility.formatToken(token.getToken()), token.getTokenValueDays(), token.getStatus());
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void expireTokens() {
        List<Token> tokenList = tokenRepo.findByStatus(ETokenStatus.NEW);
        int expireCount = 0;
        for (Token token : tokenList) {
            if (token.getPurchasedDate().plusDays(token.getTokenValueDays()).isBefore(Utility.getCurrentDateTime())) {
                token.setStatus(ETokenStatus.EXPIRED);
                tokenRepo.save(token);
                expireCount++;
            }
        }
        if (expireCount > 0) {
            System.out.println(expireCount + " tokens have been expired.");
        } else {
            System.out.println("No tokens to expire at this time.");
        }
    }


}
