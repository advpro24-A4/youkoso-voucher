package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoucherRepositoryTest {

    @Mock
    private VoucherRepository voucherRepository;

    private Voucher voucher;

    @BeforeEach
    void setUp() {
        this.voucher = new Voucher();
        this.voucher.setId(100L);
        this.voucher.setName("Discount 50%");
        this.voucher.setDiscountPercentage(0.5);
        this.voucher.setHasUsageLimit(true);
        this.voucher.setUsageLimit(100);
        this.voucher.setMinimumOrder(50000);
        this.voucher.setMaximumDiscountAmount(25000);
    }

    @Test
    void testSaveWithFindAll() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        when(voucherRepository.findAll()).thenReturn(voucherList);

        voucherRepository.save(voucher);
        List<Voucher> savedVoucherList = voucherRepository.findAll();

        assertEquals(1, savedVoucherList.size());
        assertEquals(voucher, savedVoucherList.getFirst());
    }

    @Test
    void testSavedVoucherEntityWithFindById() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        when(voucherRepository.findAll()).thenReturn(voucherList);

        voucherRepository.save(voucher);
        Voucher savedVoucher = voucherRepository.findAll().getFirst();

        assertEquals(voucher, savedVoucher);
    }

    @Test
    void testSavedVoucherAttributesWithFindById() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        when(voucherRepository.findAll()).thenReturn(voucherList);

        voucherRepository.save(voucher);
        Voucher savedVoucher = voucherRepository.findAll().getFirst();

        assertEquals(voucher.getId(), savedVoucher.getId());
        assertEquals(voucher.getName(), savedVoucher.getName());
        assertEquals(voucher.getDiscountPercentage(), savedVoucher.getDiscountPercentage());
        assertEquals(voucher.getUsageLimit(), savedVoucher.getUsageLimit());
        assertEquals(voucher.getMinimumOrder(), savedVoucher.getMinimumOrder());
        assertEquals(voucher.getMaximumDiscountAmount(), savedVoucher.getMaximumDiscountAmount());
    }

    @Test
    void testFindUnavailableVoucher() {
        when(voucherRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Voucher> obtainedVoucher = voucherRepository.findById(200L);

        assertFalse(obtainedVoucher.isPresent());
    }

    @Test
    void testFindAllIfEmpty() {
        when(voucherRepository.findAll()).thenReturn(new ArrayList<>());
        List<Voucher> voucherList = voucherRepository.findAll();

        assertTrue(voucherList.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneVoucher() {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        Voucher voucher2 = new Voucher();
        voucherList.add(voucher2);
        Voucher voucher3 = new Voucher();
        voucherList.add(voucher3);

        when(voucherRepository.findAll()).thenReturn(voucherList);
        List<Voucher> savedVouchers = voucherRepository.findAll();

        assertEquals(3, savedVouchers.size());
    }

    @Test
    void testFindAllElementsIfMoreThanOneVoucher() {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        Voucher voucher2 = new Voucher();
        voucherList.add(voucher2);
        Voucher voucher3 = new Voucher();
        voucherList.add(voucher3);

        when(voucherRepository.findAll()).thenReturn(voucherList);
        List<Voucher> savedVouchers = voucherRepository.findAll();

        assertEquals(voucher, savedVouchers.get(0));
        assertEquals(voucher2, savedVouchers.get(1));
        assertEquals(voucher3, savedVouchers.get(2));
    }

    @Test
    void testDeleteVoucherWithFindById() {
        doNothing().when(voucherRepository).deleteById(anyLong());
        voucherRepository.deleteById(voucher.getId());
        
        when(voucherRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Voucher> searchDeletedVoucher = voucherRepository.findById(voucher.getId());

        assertFalse(searchDeletedVoucher.isPresent());
    }

    @Test
    void testDeleteVoucherWithFindAll() {
        doNothing().when(voucherRepository).deleteById(anyLong());
        voucherRepository.deleteById(voucher.getId());

        List<Voucher> savedVouchers = voucherRepository.findAll();

        assertTrue(savedVouchers.isEmpty());
    }

    @Test
    void testDeleteAllVoucherWithFindAll() {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(voucher);
        Voucher voucher2 = new Voucher();
        voucherList.add(voucher2);
        Voucher voucher3 = new Voucher();
        voucherList.add(voucher3);

        doNothing().when(voucherRepository).deleteAll();
        voucherRepository.deleteAll();

        List<Voucher> savedVouchers = voucherRepository.findAll();

        assertTrue(savedVouchers.isEmpty());
    }

    @AfterEach
    void tearDown() {
        this.voucher = null;
        voucherRepository.deleteAll();
    }
}
