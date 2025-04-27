package rw.reg.Electricity.v1.interfaces;

import jakarta.validation.Valid;
import rw.reg.Electricity.v1.dtos.request.PurchaseDTO;
import rw.reg.Electricity.v1.models.Token;

import java.util.UUID;

public interface IPurchaseService {
    Token purchaseToken(@Valid PurchaseDTO dto, UUID id);
}
