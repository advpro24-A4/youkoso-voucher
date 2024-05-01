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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discountPercentage", nullable = false)
    private double discountPercentage;

    @Column(name = "hasUsageLimit", nullable = false)
    private Boolean hasUsageLimit;

    @Column(name = "usageLimit", columnDefinition = "integer default 2147483647")
    private int usageLimit = Integer.MAX_VALUE;

    @Column(name = "minimumOrder", columnDefinition = "double default 0.0")
    private double minimumOrder = 0.0;

    @Column(name = "maximumDiscountAmount", columnDefinition = "integer default 2147483647")
    private int maximumDiscountAmount = Integer.MAX_VALUE;
}