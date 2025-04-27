package rw.reg.Electricity.v1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.Token;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITokenRepository extends JpaRepository<Token, UUID> {
    Page<Token> findByMeterNumber(MeterNumber meter, Pageable pageable);

    Optional<Token> findByToken(String dto);
}
