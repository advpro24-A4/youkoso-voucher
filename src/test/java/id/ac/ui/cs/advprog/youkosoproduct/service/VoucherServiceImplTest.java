package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.model.VoucherBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceImplTest {

    @InjectMocks
    private VoucherServiceImpl service;

    @Mock
    private VoucherRepository voucherRepository;

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
        this.voucher.setId(1L);
    }

    void testCreateVoucher() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        Voucher createdVoucher = service.create(voucher);

        assertEquals(voucher, createdVoucher);
        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testDeleteVoucher() {
        long voucherId = 1L;

        service.delete(voucherId);

        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

    @Test
    void testFindAllVouchers() {
        List<Voucher> expectedList = new ArrayList<>();
        expectedList.add(voucher);

        when(voucherRepository.findAll()).thenReturn(expectedList);
        List<Voucher> actualList = service.findAll();

        assertEquals(expectedList, actualList);
        verify(voucherRepository, times(1)).findAll();
    }

    @Test
    void testFindVoucherById() {
        long voucherId = 1L;

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        Voucher foundVoucher = service.findVoucherById(voucherId);

        assertEquals(voucher, foundVoucher);
        verify(voucherRepository, times(1)).findById(voucherId);
    }

    @Test
    void testFindVoucherByIdIfNotFound() {
        long voucherId = -1L;

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        assertNull(service.findVoucherById(voucherId));
        verify(voucherRepository, times(1)).findById(voucherId);
    }

    @Test
    void testEditVoucher() {
        when(voucherRepository.findById(1L))
                .thenReturn(Optional.of(voucher));

        String editedName = "Edited Voucher";
        double editedDiscountPercentage = 0.3;
        boolean editedHasUsageLimit = true;
        int editedUsageLimit = 30;
        int editedMinimumOrder = 300000;
        int editedMaximumDiscountAmount = 30000;

        service.edit(1L, editedName, editedDiscountPercentage, editedHasUsageLimit,
                editedUsageLimit, editedMinimumOrder, editedMaximumDiscountAmount);

        assertEquals(editedName, voucher.getName());
        assertEquals(editedDiscountPercentage, voucher.getDiscountPercentage());
        assertEquals(editedHasUsageLimit, voucher.getHasUsageLimit());
        assertEquals(editedUsageLimit, voucher.getUsageLimit());
        assertEquals(editedMinimumOrder, voucher.getMinimumOrder());
        assertEquals(editedMaximumDiscountAmount, voucher.getMaximumDiscountAmount());

        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testEditVoucherWithDefaultAttributesShouldRemainsUnchanged() {
        when(voucherRepository.findById(1L))
                .thenReturn(Optional.of(voucher));

        Voucher voucherWithDefault = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(0.5)
                .hasUsageLimit(false)
                .build();

        String editedName = "Edited Voucher";
        double editedDiscountPercentage = 0.3;
        boolean editedHasUsageLimit = true;

        service.edit(1L, editedName, editedDiscountPercentage, editedHasUsageLimit,
                voucherWithDefault.getUsageLimit(), voucherWithDefault.getMinimumOrder(),
                voucherWithDefault.getMaximumDiscountAmount());

        assertEquals(editedName, voucher.getName());
        assertEquals(editedDiscountPercentage, voucher.getDiscountPercentage());
        assertEquals(editedHasUsageLimit, voucher.getHasUsageLimit());
        assertEquals(Integer.MAX_VALUE, voucher.getUsageLimit());
        assertEquals(0.0, voucher.getMinimumOrder());
        assertEquals(Integer.MAX_VALUE, voucher.getMaximumDiscountAmount());

        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testEditVoucherNotFound() {
        when(voucherRepository.findById(-1L))
                .thenReturn(Optional.empty());

        String editedName = "Edited Voucher";
        double editedDiscountPercentage = 0.3;
        boolean editedHasUsageLimit = true;
        int editedUsageLimit = 30;
        int editedMinimumOrder = 300000;
        int editedMaximumDiscountAmount = 30000;

        assertThrows(IllegalArgumentException.class, () -> {
            service.edit(-1L, editedName, editedDiscountPercentage, editedHasUsageLimit,
                    editedUsageLimit, editedMinimumOrder, editedMaximumDiscountAmount);
        });

        verify(voucherRepository, never()).save(any());
    }
}
