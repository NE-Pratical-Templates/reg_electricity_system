package rw.reg.Electricity.v1.interfaces;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.reg.Electricity.v1.dtos.request.CreateMeterDTO;
import rw.reg.Electricity.v1.models.MeterNumber;

import java.util.UUID;

public interface IMeterService {
    MeterNumber createMeter(@Valid CreateMeterDTO dto, UUID customerID);

    Page<MeterNumber> getAllMeterNumbers(Pageable pageable);

    Page<MeterNumber> getAllMeterNumbersByCustomer(Pageable pageable, UUID customerID);

    Page<MeterNumber> search(Pageable pageable, String q);
}
