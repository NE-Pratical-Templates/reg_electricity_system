package rw.reg.Electricity.v1.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rw.reg.Electricity.v1.enums.ETokenStatus;
import rw.reg.Electricity.v1.exceptions.ResourceNotFoundException;
import rw.reg.Electricity.v1.interfaces.IMessageService;
import rw.reg.Electricity.v1.models.Message;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.Token;
import rw.reg.Electricity.v1.models.User;
import rw.reg.Electricity.v1.repositories.ITokenRepository;
import rw.reg.Electricity.v1.repositories.IUserRepository;
import rw.reg.Electricity.v1.security.IMessageRepository;
import rw.reg.Electricity.v1.standalone.MailService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageImpl implements IMessageService {
    private final IMessageRepository messageRepo;
    private final ITokenRepository tokenRepo;
    private final IUserRepository userRepo;
    private final MailService mailService;


    @Override
    public Void notifyCustomer(String dto) {
        Token token = tokenRepo.findByToken(dto).orElseThrow(() -> new ResourceNotFoundException(" no token found , please enter valid token  ", "token", dto));
        MeterNumber meter = token.getMeterNumber();
        User customer = meter.getUser();
        String message = "Dear " + customer.getFullName() + ", REG is pleased to remind you that the token in the " +
                meter.getMeterNumber() + " is going to expire in 5 hours. Please purchase a new token.";
        // Save the message to the repository (e.g., for logging or future use)
        Message msg = new Message();
        msg.setMeterNumber(meter);
        msg.setMessage(message);
        messageRepo.save(msg);
        token.setStatus(ETokenStatus.EXPIRED);
        tokenRepo.save(token);
        mailService.sendTokenExpiryNotification(customer.getEmail(), customer.getFullName(), meter.getMeterNumber(), message);
        return null;
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void checkExpiringTokens() {
        log.info("Checking for tokens expiring in the next 5 hours");

        List<Token> expiringTokens = tokenRepo.findTokensExpiringInFiveHours();
        for (Token token : expiringTokens) {
            MeterNumber meter = token.getMeterNumber();
            User customer = meter.getUser();
            String message = "Dear " + customer.getFullName() + ", REG is pleased to remind you that the token in the " +
                    meter.getMeterNumber() + " is going to expire in 5 hours. Please purchase a new token.";
            Message msg = new Message();
            msg.setMeterNumber(meter);
            msg.setMessage(message);
            messageRepo.save(msg);
            mailService.sendTokenExpiryNotification(customer.getEmail(), customer.getFullName(), meter.getMeterNumber(), message);
            token.setExpiringMsgSent(true);
            tokenRepo.save(token);
        }
        log.info("Token expiry check completed. {} tokens found expiring in the next 5 hours.", expiringTokens.size());
    }
}
