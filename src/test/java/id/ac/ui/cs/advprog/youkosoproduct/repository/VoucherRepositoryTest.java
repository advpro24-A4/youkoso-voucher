package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherRepositoryTest {
    
    @InjectMocks
    private VoucherRepository voucherRepository;

    @Test
    void testSaveAndFind() {
        Voucher voucher = new Voucher();
        voucher.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher.setName("Discount 50%");
        voucher.setDiscountAmount(0.5);
        voucher.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher);

        List<Voucher> voucherList = voucherRepository.findAll();
        assertFalse(voucherList.isEmpty());

        Voucher savedVoucher = voucherList.get(0);
        assertEquals(voucher.getId(), savedVoucher.getId());
        assertEquals(voucher.getName(), savedVoucher.getName());
        assertEquals(voucher.getDiscountAmount(), savedVoucher.getDiscountAmount());
        assertEquals(voucher.getMaxUsage(), savedVoucher.getMaxUsage());
    }

    @Test
    void testFindUnavailableVoucher() {
        Voucher voucher1 = new Voucher();
        voucher1.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher1.setName("Discount 50%");
        voucher1.setDiscountAmount(0.5);
        voucher1.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher1);

        Voucher voucher2 = new Voucher();
        voucher2.setId("4a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher2.setName("Discount 40%");
        voucher2.setDiscountAmount(0.4);
        voucher2.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher2);

        Optional<Voucher> obtainedVoucher = voucherRepository.findById("z0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertNull(obtainedVoucher.orElse(null));
    }

    @Test
    void testFindAllIfEmpty() {
        List<Voucher> voucherList = voucherRepository.findAll();
        assertTrue(voucherList.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneVoucher() {
        Voucher voucher1 = new Voucher();
        voucher1.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher1.setName("Discount 50%");
        voucher1.setDiscountAmount(0.5);
        voucher1.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher1);

        Voucher voucher2 = new Voucher();
        voucher2.setId("4a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher2.setName("Discount 40%");
        voucher2.setDiscountAmount(0.4);
        voucher2.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher2);

        Voucher voucher3 = new Voucher();
        voucher3.setId("3a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher3.setName("Discount 30%");
        voucher3.setDiscountAmount(0.3);
        voucher3.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher3);

        List<Voucher> voucherList = voucherRepository.findAll();
        assertEquals(3, voucherList.size());
    }

    @Test
    void testDeleteVoucher() {
        Voucher voucher = new Voucher();
        voucher.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        voucher.setName("Discount 50%");
        voucher.setDiscountAmount(0.5);
        voucher.setMaxUsage(Integer.MAX_VALUE);
        voucherRepository.save(voucher);

        voucherRepository.save(voucher);
        voucherRepository.deleteById(voucher.getId());

        Optional<Voucher> searchDeletedVoucher = voucherRepository.findById(voucher.getId());
        assertFalse(searchDeletedVoucher.isPresent());
    }
}
