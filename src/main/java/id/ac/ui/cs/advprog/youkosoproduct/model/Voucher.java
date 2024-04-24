package id.ac.ui.cs.advprog.youkosoproduct.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discountAmount", nullable = false)
    private double discountAmount;

    @Column(name = "maxUsage", nullable = false)
    private int maxUsage;
}