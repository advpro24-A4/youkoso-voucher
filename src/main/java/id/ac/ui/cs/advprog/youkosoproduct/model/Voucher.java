package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    private int discountPercentage;

    @Column(name = "hasUsageLimit", nullable = false)
    private Boolean hasUsageLimit;

    @Column(name = "usageLimit", columnDefinition = "integer default 2147483647")
    private int usageLimit = Integer.MAX_VALUE;

    @Column(name = "minimumOrder", columnDefinition = "integer default 0")
    private int minimumOrder = 0;

    @Column(name = "maximumDiscountAmount", columnDefinition = "integer default 2147483647")
    private int maximumDiscountAmount = Integer.MAX_VALUE;

    public boolean isValid() {
        return (discountPercentage >= 0 && discountPercentage <= 100) &&
                usageLimit >= 0 &&
                minimumOrder >= 0 &&
                maximumDiscountAmount >= 0;
    }

    public void decrementUsageLimit() {
        usageLimit--;
    }
}