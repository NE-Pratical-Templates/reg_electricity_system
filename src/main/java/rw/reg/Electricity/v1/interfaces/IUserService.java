package rw.reg.Electricity.v1.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.reg.Electricity.v1.dtos.response.UserResponseDTO;
import rw.reg.Electricity.v1.models.User;

import java.util.UUID;

public interface IUserService {
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    User getUserByID(UUID id);

    Page<User> search(Pageable pageable, String q);
}
