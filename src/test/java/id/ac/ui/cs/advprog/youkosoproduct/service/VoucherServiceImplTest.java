package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
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
        this.voucher = new Voucher();
        this.voucher.setId("5a5c06a3-4827-44fa-b8bf-cdc117f5e731");
        this.voucher.setName("Discount 50%");
        this.voucher.setDiscountAmount(0.5);
        this.voucher.setMaxUsage(Integer.MAX_VALUE);
    }

    void testCreateVoucher() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        Voucher createdVoucher = service.create(voucher);

        assertEquals(voucher, createdVoucher);
        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testDeleteVoucher() {
        String voucherId = "5a5c06a3-4827-44fa-b8bf-cdc117f5e731";
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
        String voucherId = "5a5c06a3-4827-44fa-b8bf-cdc117f5e731";
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));

        Voucher foundVoucher = service.findVoucherById(voucherId);

        assertEquals(voucher, foundVoucher);
        verify(voucherRepository, times(1)).findById(voucherId);
    }

    @Test
    void testFindVoucherByIdNotFound() {
        String voucherId = "0a5c06a3-4827-44fa-b8bf-cdc117f5e731";
        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        assertNull(service.findVoucherById(voucherId));
        verify(voucherRepository, times(1)).findById(voucherId);
    }

    @Test
    void testEditVoucher() {
        when(voucherRepository.findById("5a5c06a3-4827-44fa-b8bf-cdc117f5e731"))
                .thenReturn(Optional.of(voucher));

        String editedName = "Edited Voucher";
        double editedDiscountAmount = 0.7;
        int editedMaxUsage = 20;

        service.edit("5a5c06a3-4827-44fa-b8bf-cdc117f5e731", editedName,
                editedDiscountAmount, editedMaxUsage);

        assertEquals(editedName, voucher.getName());
        assertEquals(editedDiscountAmount, voucher.getDiscountAmount());
        assertEquals(editedMaxUsage, voucher.getMaxUsage());

        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testEditVoucherNotFound() {
        when(voucherRepository.findById("5a5c06a3-4827-44fa-b8bf-cdc117f5e731"))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.edit("5a5c06a3-4827-44fa-b8bf-cdc117f5e731",
                    "Edited Voucher", 0.7, 20);
        });

        verify(voucherRepository, never()).save(any());
    }
}
