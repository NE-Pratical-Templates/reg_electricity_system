package rw.reg.Electricity.v1.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageImpl implements IMessageService {
    private final IMessageRepository messageRepo;
    private final ITokenRepository tokenRepo;
    private final IUserRepository userRepo;
    private final MailService mailService;

//TODO:   add automatic notification
    //    @Scheduled(fixedRate = 3600000)
//    @Scheduled(fixedRate = 60000)
//    @Transactional
//    public void checkAndNotifyExpiredTokens() {
//        LocalDateTime now = LocalDateTime.now(); // Current time
//        LocalDateTime expirationWindow = now.plusHours(5); // 5 hours from now
//
//        log.info("Checking tokens expiring in the next 5 hours");
//
//        // Fetch tokens that have a purchased date and expiration date within the next 5 hours
//        List<Token> expiringTokens = tokenRepo.findTokensExpiringInNext5Hours(now, expirationWindow);
//
//        for (Token token : expiringTokens) {
//            // Calculate the actual expiration date for the token
//            LocalDateTime tokenExpirationDate = token.getPurchasedDate().plusDays(token.getTokenValueDays());
//
//            // If the expiration date is within the next 5 hours OR the token is already expired, proceed with the notification
//            if (tokenExpirationDate.isBefore(expirationWindow)) {
//                MeterNumber meterNumber = token.getMeterNumber();
//                User customer = meterNumber.getUser();
//
//                // Construct the notification message
//                String message = "Dear " + customer.getFullName() + ", REG is pleased to remind you that the token in the " +
//                        meterNumber.getMeterNumber() + " is going to expire in 5 hours. Please purchase a new token.";
//
//                // Save the message to the repository (e.g., for logging or future use)
//                Message msg = new Message();
//                msg.setMeterNumber(meterNumber);
//                msg.setMessage(message);
//                messageRepo.save(msg);
//                token.setStatus(ETokenStatus.EXPIRED);
//                tokenRepo.save(token);
//
//                // Send email notification
//                mailService.sendTokenExpiryNotification(customer.getEmail(), customer.getFullName(), meterNumber.getMeterNumber(), message);
//
//                // Log the notification action for auditing purposes
//                log.info("Sent token expiry notification to: {}, Meter Number: {}, Message: {}", customer.getEmail(), meterNumber.getMeterNumber(), message);
//            } else {
//                log.info("Token with Meter Number: {} has expired or is not expiring in the next 5 hours. Expiration Date: {}",
//                        token.getMeterNumber().getMeterNumber(), tokenExpirationDate);
//            }
//        }
//    }

//    @Scheduled(fixedRate = 60000)
//    @Transactional
//    public void generateNotifications() {
//        log.info("calling one real one ");
//        // Call the stored procedure to generate notifications
//        entityManager.createNativeQuery("CALL generate_notification_message()").executeUpdate();
//    }

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
}
