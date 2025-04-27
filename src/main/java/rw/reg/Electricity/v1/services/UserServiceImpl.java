package rw.reg.Electricity.v1.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.reg.Electricity.v1.dtos.response.UserResponseDTO;
import rw.reg.Electricity.v1.interfaces.IUserService;
import rw.reg.Electricity.v1.models.User;
import rw.reg.Electricity.v1.repositories.IUserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepo;

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable)
                .map(this::convertToDto);
    }

    @Override
    public User getUserByID(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

//     search user
    @Override
    public Page<User> search(Pageable pageable, String searchKey) {
        return userRepo.search(pageable, searchKey);
    }

    private UserResponseDTO convertToDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getNationalId(),
                user.getMobile(),
                user.getDob()
        );
    }
}
