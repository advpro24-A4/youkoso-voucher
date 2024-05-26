package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.youkosoproduct.model.builder.VoucherBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoucherBuilderTest {
    private Voucher voucher;
    private Voucher voucherWithDefault;

    @BeforeEach
    void setUp() {
        this.voucher = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(50)
                .hasUsageLimit(true)
                .usageLimit(100)
                .minimumOrder(50000)
                .maximumDiscountAmount(25000)
                .build();

        this.voucherWithDefault = new VoucherBuilder()
                .name("Discount 20%")
                .discountPercentage(20)
                .hasUsageLimit(false)
                .build();
    }

    @Test
    void testBuildAndGetVoucherName() {
        assertEquals("Discount 50%", this.voucher.getName());
    }

    @Test
    void testBuildAndGetVoucherDiscountPercentage() {
        assertEquals(50, this.voucher.getDiscountPercentage());
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
        assertEquals(Integer.MAX_VALUE, this.voucherWithDefault.getUsageLimit());
    }

    @Test
    void testBuildAndGetMinimumOrderIfDefault() {
        assertEquals(0, this.voucherWithDefault.getMinimumOrder());
    }

    @Test
    void testBuildAndGetMaximumDiscountAmountIfDefault() {
        assertEquals(Integer.MAX_VALUE, this.voucherWithDefault.getMaximumDiscountAmount());
    }

    @AfterEach
    void tearDown() {
        this.voucher = null;
    }
}
