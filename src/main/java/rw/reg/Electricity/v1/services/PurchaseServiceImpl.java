package rw.reg.Electricity.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.reg.Electricity.v1.dtos.request.PurchaseDTO;
import rw.reg.Electricity.v1.enums.ETokenStatus;
import rw.reg.Electricity.v1.exceptions.BadRequestException;
import rw.reg.Electricity.v1.exceptions.ResourceNotFoundException;
import rw.reg.Electricity.v1.interfaces.IPurchaseService;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.Token;
import rw.reg.Electricity.v1.models.User;
import rw.reg.Electricity.v1.repositories.IMeterRepository;
import rw.reg.Electricity.v1.repositories.ITokenRepository;
import rw.reg.Electricity.v1.repositories.IUserRepository;
import rw.reg.Electricity.v1.utils.Constants;
import rw.reg.Electricity.v1.utils.Utility;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements IPurchaseService {
    private final ITokenRepository tokenRepo;
    private final IMeterRepository meterRepo;
    private final IUserRepository userRepo;

    @Override
    public Token purchaseToken(PurchaseDTO dto, UUID id) {
        if (dto.getAmount() < 100 || dto.getAmount() % 100 != 0) {
            throw new BadRequestException("Amount must be a multiple of 100 RWF and at least 100 RWF.");
        }

        int tokenValueDays = (int) (dto.getAmount() / 100);
        if (tokenValueDays > Constants.MAX_DAYS) {
            throw new BadRequestException("Token validity cannot exceed 5 years.");
        }
        User customer = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException(" no use found with tha id "));
        MeterNumber meter = meterRepo.findByMeterNumber(dto.getMeterNumber()).orElseThrow(() -> new ResourceNotFoundException(" no meter found with that number ", "meter_number", dto.getMeterNumber()));
        if (meter.getUser().getId() != customer.getId()) {
            throw new BadRequestException("You can't purchase with this meter number because it is not yours.");
        }

        String token = Utility.generateMeterToken();
        Token purchase = new Token();
        purchase.setMeterNumber(meter);
        purchase.setToken(token);
        purchase.setStatus(ETokenStatus.NEW);
        purchase.setTokenValueDays(tokenValueDays);
        purchase.setAmount(dto.getAmount());
        return tokenRepo.save(purchase);
    }
}
