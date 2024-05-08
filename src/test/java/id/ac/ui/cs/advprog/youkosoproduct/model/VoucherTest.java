package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoucherTest {
    private Voucher voucher;
    private Voucher voucherWithDefault;
    private Voucher invalidVoucher;

    @BeforeEach
    void setUp() {
        this.voucher = new Voucher();
        this.voucher.setId(1L);
        this.voucher.setName("Discount 50%");
        this.voucher.setDiscountPercentage(50);
        this.voucher.setHasUsageLimit(true);
        this.voucher.setUsageLimit(100);
        this.voucher.setMinimumOrder(50000);
        this.voucher.setMaximumDiscountAmount(25000);
        
        this.voucherWithDefault = new Voucher();
        this.voucherWithDefault.setId(2L);
        this.voucherWithDefault.setName("Discount 20%");
        this.voucherWithDefault.setDiscountPercentage(20);
        this.voucherWithDefault.setHasUsageLimit(false);

        this.invalidVoucher = new Voucher();
    }

    @Test
    void testSetAndGetVoucherId() {
        assertEquals(1L, this.voucher.getId());
    }

    @Test
    void testSetAndGetVoucherName() {
        assertEquals("Discount 50%", this.voucher.getName());
    }

    @Test
    void testSetAndGetVoucherDiscountPercentage() {
        assertEquals(50, this.voucher.getDiscountPercentage());
    }

    @Test
    void testSetAndGetHasUsageLimit() {
        assertEquals(true, this.voucher.getHasUsageLimit());
    }

    @Test
    void testSetAndGetUsageLimit() {
        assertEquals(100, this.voucher.getUsageLimit());
    }

    @Test
    void testSetAndGetMinimumOrder() {
        assertEquals(50000, this.voucher.getMinimumOrder());
    }

    @Test
    void testSetAndGetMaximumDiscountAmount() {
        assertEquals(25000, this.voucher.getMaximumDiscountAmount());
    }

    @Test
    void testGetUsageLimitIfDefault() {
        assertEquals(Integer.MAX_VALUE, this.voucherWithDefault.getUsageLimit());
    }

    @Test
    void testSetAndGetMinimumOrderIfDefault() {
        assertEquals(0, this.voucherWithDefault.getMinimumOrder());
    }

    @Test
    void testSetAndGetMaximumDiscountAmountIfDefault() {
        assertEquals(Integer.MAX_VALUE, this.voucherWithDefault.getMaximumDiscountAmount());
    }

    @Test
    void testIsValidIfValid() {
        assertTrue(this.voucher.isValid());
    }

    @Test
    void testIsValidIfDiscountPercentageNegative() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setDiscountPercentage(-1);
        assertFalse(invalidVoucher.isValid());
    }

    @Test
    void testIsValidIfDiscountPercentageGreaterThan100() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setDiscountPercentage(101);
        assertFalse(invalidVoucher.isValid());
    }

    @Test
    void testIsValidIfUsageLimitNegative() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setUsageLimit(-1);
        assertFalse(invalidVoucher.isValid());
    }

    @Test
    void testIsValidIfMinimumOrderNegative() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setMinimumOrder(-1);
        assertFalse(invalidVoucher.isValid());
    }

    @Test
    void testIsValidIfMaximumDiscountAmountNegative() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setMaximumDiscountAmount(-1);
        assertFalse(invalidVoucher.isValid());
    }

    @AfterEach
    void tearDown() {
        this.voucher = null;
    }
}
