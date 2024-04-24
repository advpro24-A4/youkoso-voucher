package id.ac.ui.cs.advprog.youkosoproduct.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoucherTest {
    Voucher voucher;

    @BeforeEach
    void setUp() {
        this.voucher = new Voucher();
        this.voucher.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        this.voucher.setName("Discount 50%");
        this.voucher.setDiscountAmount(0.5);
        this.voucher.setMaxUsage(Integer.MAX_VALUE);
    }

    @Test
    void testGetVoucherId() {
        assertEquals("5a5c06a3-4827-44fa-b8bf-cdc117f5e731", this.voucher.getId());
    }

    @Test
    void testGetVoucherName() {
        assertEquals("Discount 50%", this.voucher.getName());
    }

    @Test
    void testGetVoucherDiscountAmount() {
        assertEquals(0.5, this.voucher.getDiscountAmount());
    }

    @Test
    void testGetVoucherMaxUsage() {
        assertEquals(Integer.MAX_VALUE, this.voucher.getMaxUsage());
    }

    @AfterEach
    void tearDown() {
        this.voucher = null;
    }
}
