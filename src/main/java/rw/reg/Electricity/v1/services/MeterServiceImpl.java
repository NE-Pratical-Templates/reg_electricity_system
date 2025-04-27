package rw.reg.Electricity.v1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.reg.Electricity.v1.dtos.request.CreateMeterDTO;
import rw.reg.Electricity.v1.exceptions.ResourceNotFoundException;
import rw.reg.Electricity.v1.interfaces.IMeterService;
import rw.reg.Electricity.v1.models.MeterNumber;
import rw.reg.Electricity.v1.models.User;
import rw.reg.Electricity.v1.repositories.IMeterRepository;
import rw.reg.Electricity.v1.repositories.IUserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements IMeterService {
    private final IMeterRepository meterRepo;
    private final IUserRepository userRepo;

    @Override
    public MeterNumber createMeter(CreateMeterDTO dto, UUID customerID) {
        User user = userRepo.findById(customerID).orElseThrow(() -> new UsernameNotFoundException(" no user found wit that id "));
        MeterNumber meter = new MeterNumber();
        meter.setMeterNumber(dto.getMeterNumber());
        meter.setUser(user);

        return meterRepo.save(meter);
    }

//     get all meter numbers

    @Override
    public Page<MeterNumber> getAllMeterNumbers(Pageable pageable) {
        return meterRepo.findAll(pageable);
    }


    //     get all meter numbers of  customer
    @Override
    public Page<MeterNumber> getAllMeterNumbersByCustomer(Pageable pageable, UUID userID) {
        User user = userRepo.findById(userID).orElseThrow(() -> new UsernameNotFoundException(" no user found wit that id "));

        return meterRepo.findByUser(user, pageable);
    }

    //     search meter number
    @Override
    public Page<MeterNumber> search(Pageable pageable, String searchKey) {
        return meterRepo.search(pageable, searchKey);
    }
}
