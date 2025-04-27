package rw.reg.Electricity.v1.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.reg.Electricity.v1.audits.InitiatorAudit;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "meter_numbers")

public class MeterNumber extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(unique = true, length = 6)
    private String meterNumber;

    @ManyToOne
    private User user;
}
