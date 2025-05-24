package rw.reg.Electricity.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.reg.Electricity.v1.enums.ETokenStatus;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITokenRepository extends JpaRepository<Token, UUID> {
    Page<Token> findByMeterNumber(MeterNumber meter, Pageable pageable);

    Optional<Token> findByToken(String dto);

    List<Token> findByStatus(ETokenStatus eTokenStatus);

    @Query(value = "SELECT * FROM purchased_tokens t WHERE t.status = 'NEW' AND t.expiring_msg_sent=false " +
            "AND (t.purchased_date + (t.token_value_days || ' days')::interval) BETWEEN " +
            "CURRENT_TIMESTAMP + INTERVAL '5 hours' AND CURRENT_TIMESTAMP + INTERVAL '6 hours'",
            nativeQuery = true)
    List<Token> findTokensExpiringInFiveHours();
}
