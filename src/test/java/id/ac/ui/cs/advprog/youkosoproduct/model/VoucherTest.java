package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoucherTest {
    private VoucherBuilder builder;
    private Voucher voucher;

    @BeforeEach
    void setUp() {
        this.voucher = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(0.5)
                .hasUsageLimit(true)
                .usageLimit(100)
                .minimumOrder(50000)
                .maximumDiscountAmount(25000)
                .build();
    }

    @Test
    void testBuildAndGetVoucherName() {
        assertEquals("Discount 50%", this.voucher.getName());
    }

    @Test
    void testBuildAndGetVoucherDiscountPercentage() {
        assertEquals(0.5, this.voucher.getDiscountPercentage());
    }

    @Test
    void testBuildAndGetHasUsageLimit() {
        assertEquals(true, this.voucher.getHasUsageLimit());
    }

    @Test
    void testBuildAndGetUsageLimit() {
        assertEquals(100, this.voucher.getUsageLimit());
    }

    @Test
    void testBuildAndGetMinimumOrder() {
        assertEquals(50000, this.voucher.getMinimumOrder());
    }

    @Test
    void testBuildAndGetMaximumDiscountAmount() {
        assertEquals(25000, this.voucher.getMaximumDiscountAmount());
    }

    @Test
    void testGetUsageLimitIfDefault() {
        Voucher voucherWithDefault = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(0.5)
                .hasUsageLimit(true)
                .build();
        assertEquals(Integer.MAX_VALUE, voucherWithDefault.getUsageLimit());
    }

    @Test
    void testBuildAndGetMinimumOrderIfDefault() {
        Voucher voucherWithDefault = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(0.5)
                .hasUsageLimit(true)
                .build();
        assertEquals(0.0, voucherWithDefault.getMinimumOrder());
    }

    @Test
    void testBuildAndGetMaximumDiscountAmountIfDefault() {
        Voucher voucherWithDefault = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(0.5)
                .hasUsageLimit(true)
                .build();
        assertEquals(Integer.MAX_VALUE, voucherWithDefault.getMaximumDiscountAmount());
    }

    @AfterEach
    void tearDown() {
        this.builder = null;
        this.voucher = null;
    }
}
