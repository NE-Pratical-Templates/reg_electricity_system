package rw.reg.Electricity.v1.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import rw.reg.Electricity.v1.audits.InitiatorAudit;
import rw.reg.Electricity.v1.enums.EAccountStatus;
import rw.reg.Electricity.v1.enums.ETokenStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tokens")

public class Token extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private MeterNumber meterNumber;

    @Column(length = 16, name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ETokenStatus status = ETokenStatus.NEW;

    @Column(name = "token_value_days")
    private int tokenValueDays;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "purchased_date")
    private LocalDateTime purchasedDate;

    @Column(name = "amount")
    private Double amount;
}
