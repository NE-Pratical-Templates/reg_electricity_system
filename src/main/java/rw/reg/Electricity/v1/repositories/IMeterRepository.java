package rw.reg.Electricity.v1.repositories;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IMeterRepository extends JpaRepository<MeterNumber, UUID> {
    Page<MeterNumber> findByUser(User user, Pageable pageable);

    @Query("SELECT m FROM MeterNumber m " +
            "LEFT JOIN m.user u " +
            "WHERE (lower(m.meterNumber) LIKE lower(concat('%', :searchKey, '%'))) " +
            "OR (lower(u.firstName) LIKE lower(concat('%', :searchKey, '%'))) " +
            "OR (lower(u.lastName) LIKE lower(concat('%', :searchKey, '%'))) " +
            "OR (lower(u.email) LIKE lower(concat('%', :searchKey, '%'))) " +
            "OR (lower(u.nationalId) LIKE lower(concat('%', :searchKey, '%'))) ")
    Page<MeterNumber> search(Pageable pageable, String searchKey);

    Optional<MeterNumber> findByMeterNumber(String meterNumber);
}
