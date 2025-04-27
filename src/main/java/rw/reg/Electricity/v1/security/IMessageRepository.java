package rw.reg.Electricity.v1.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.reg.Electricity.v1.models.Message;

import java.util.UUID;
@Repository
public interface IMessageRepository extends JpaRepository<Message, UUID> {
}
