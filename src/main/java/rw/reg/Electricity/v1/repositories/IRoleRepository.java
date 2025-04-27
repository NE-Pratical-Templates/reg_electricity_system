package rw.reg.Electricity.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.reg.Electricity.v1.enums.ERole;
import rw.reg.Electricity.v1.models.Role;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(ERole role);

    boolean existsByName(ERole role);
}
