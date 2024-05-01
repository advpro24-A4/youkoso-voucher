package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoucherTest {
    private Voucher voucher;

    @BeforeEach
    void setUp() {
        this.voucher = new Voucher();
    }

    @Test
    void testSetAndSetAndGetVoucherId() {
        this.voucher.setId(100L);
        assertEquals(100L, this.voucher.getId());
    }

    @Test
    void testSetAndGetVoucherName() {
        this.voucher.setName("Discount 50%");
        assertEquals("Discount 50%", this.voucher.getName());
    }

    @Test
    void testSetAndGetVoucherDiscountPercentage() {
        this.voucher.setDiscountPercentage(0.5);
        assertEquals(0.5, this.voucher.getDiscountPercentage());
    }

    @Test
    void testSetAndGetHasUsageLimit() {
        this.voucher.setHasUsageLimit(true);
        assertEquals(true, this.voucher.getHasUsageLimit());
    }

    @Test
    void testSetAndGetUsageLimit() {
        this.voucher.setUsageLimit(100);
        assertEquals(100, this.voucher.getUsageLimit());
    }

    @Test
    void testSetAndGetMinimumOrder() {
        this.voucher.setMinimumOrder(50000);
        assertEquals(50000, this.voucher.getMinimumOrder());
    }

    @Test
    void testSetAndGetMaximumDiscountAmount() {
        this.voucher.setMaximumDiscountAmount(25000);
        assertEquals(25000, this.voucher.getMaximumDiscountAmount());
    }

    @Test
    void testGetUsageLimitIfDefault() {
        assertEquals(Integer.MAX_VALUE, this.voucher.getUsageLimit());
    }

    @Test
    void testSetAndGetMinimumOrderIfDefault() {
        assertEquals(0.0, this.voucher.getMinimumOrder());
    }

    @Test
    void testSetAndGetMaximumDiscountAmountIfDefault() {
        assertEquals(Integer.MAX_VALUE, this.voucher.getMaximumDiscountAmount());
    }

    @AfterEach
    void tearDown() {
        this.voucher = null;
    }
}
