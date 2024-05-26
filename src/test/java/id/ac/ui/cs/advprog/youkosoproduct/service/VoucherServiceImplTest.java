package id.ac.ui.cs.advprog.youkosoproduct.service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.model.VoucherBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.repository.PaymentRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceImplTest {

    @InjectMocks
    private VoucherServiceImpl service;

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private PaymentRepository paymentRepository;

    private Voucher voucher;

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
        this.voucher.setId(1L);
    }

    @Test
    void testCreateVoucher() {
        when(voucherRepository.save(voucher)).thenReturn(voucher);

        Voucher createdVoucher = service.create(voucher);

        assertEquals(voucher, createdVoucher);
        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testCreateVoucherInvalidAttribute() {
        Voucher invalidVoucher = new Voucher();
        invalidVoucher.setDiscountPercentage(101);
        invalidVoucher.setUsageLimit(-1);
        invalidVoucher.setMinimumOrder(-1);
        invalidVoucher.setMaximumDiscountAmount(-1);

        assertThrows(IllegalArgumentException.class,
                () -> service.create(invalidVoucher));

        verify(voucherRepository, times(0)).save(voucher);
    }

    @Test
    void testDeleteVoucher() {
        Long voucherId = 1L;

        when(voucherRepository.existsById(voucherId)).thenReturn(true);

        service.delete(voucherId);

        verify(voucherRepository, times(1)).deleteById(voucherId);
    }

    @Test
    void testDeleteNonExistentVoucher() {
        Long voucherId = 3L;

        when(voucherRepository.existsById(voucherId)).thenReturn(false);
        
        assertThrows(IllegalArgumentException.class,
                () -> service.delete(voucherId));

        verify(voucherRepository, times(0)).deleteById(voucherId);
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
        when(voucherRepository.findById(1L)).thenReturn(Optional.of(this.voucher));

        Voucher obtainedVoucher = service.findVoucherById(1L);

        assertEquals(voucher, obtainedVoucher);
        verify(voucherRepository, times(1)).findById(1L);
    }

    @Test
    void testFindVoucherByIdNotFound() {
        when(voucherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.findVoucherById(2L));
        verify(voucherRepository, times(1)).findById(2L);
    }

    @Test
    void testEditVoucher() {
        when(voucherRepository.findById(1L))
                .thenReturn(Optional.of(voucher));

        String editedName = "Edited Voucher";
        int editedDiscountPercentage = 30;
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
        Long voucherId = 1L;

        when(voucherRepository.findById(voucherId))
                .thenReturn(Optional.of(voucher));

        Voucher voucherWithDefault = new VoucherBuilder()
                .name("Discount 50%")
                .discountPercentage(50)
                .hasUsageLimit(false)
                .build();

        String editedName = "Edited Voucher";
        int editedDiscountPercentage = 30;
        boolean editedHasUsageLimit = true;

        service.edit(1L, editedName, editedDiscountPercentage, editedHasUsageLimit,
                voucherWithDefault.getUsageLimit(), voucherWithDefault.getMinimumOrder(),
                voucherWithDefault.getMaximumDiscountAmount());

        assertEquals(editedName, voucher.getName());
        assertEquals(editedDiscountPercentage, voucher.getDiscountPercentage());
        assertEquals(editedHasUsageLimit, voucher.getHasUsageLimit());
        assertEquals(Integer.MAX_VALUE, voucher.getUsageLimit());
        assertEquals(0, voucher.getMinimumOrder());
        assertEquals(Integer.MAX_VALUE, voucher.getMaximumDiscountAmount());

        verify(voucherRepository, times(1)).save(voucher);
    }

    @Test
    void testEditVoucherNotFound() {
        Long voucherId = 2L;

        when(voucherRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        String editedName = "Edited Voucher";
        int editedDiscountPercentage = 30;
        boolean editedHasUsageLimit = true;
        int editedUsageLimit = 30;
        int editedMinimumOrder = 300000;
        int editedMaximumDiscountAmount = 30000;

        assertThrows(IllegalArgumentException.class, () -> {
            service.edit(voucherId, editedName, editedDiscountPercentage, editedHasUsageLimit,
                    editedUsageLimit, editedMinimumOrder, editedMaximumDiscountAmount);
        });

        verify(voucherRepository, never()).save(any());
    }

    @Test
    void testEditVoucherInvalidAttributes() {
        Long voucherId = 1L;

        String editedName = "Edited Voucher";
        int editedDiscountPercentage = -1;
        boolean editedHasUsageLimit = true;
        int editedUsageLimit = -1;
        int editedMinimumOrder = -1;
        int editedMaximumDiscountAmount = -1;

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(this.voucher));

        assertThrows(IllegalArgumentException.class, () -> {
            service.edit(voucherId, editedName, editedDiscountPercentage, editedHasUsageLimit,
                    editedUsageLimit, editedMinimumOrder, editedMaximumDiscountAmount);
        });

        verify(voucherRepository, times(0)).save(voucher);
    }

        @Test
    public void testUseVoucherHappyPath() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";

        Voucher voucher = new Voucher();
        voucher.setId(voucherId);
        voucher.setDiscountPercentage(10);
        voucher.setHasUsageLimit(true);
        voucher.setUsageLimit(5);
        voucher.setMinimumOrder(100);
        voucher.setMaximumDiscountAmount(50);

        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setUserId(userId);
        payment.setTotalPrice(200);

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        service.useVoucher(voucherId, paymentId, userId);

        // Assertions
        // You can assert the updated values of the voucher and payment objects
    }

    @Test
    public void testUseVoucherInvalidVoucherId() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.useVoucher(voucherId, paymentId, userId);
        });
    }

    @Test
    public void testUseVoucherInvalidPaymentId() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";

        Voucher voucher = new Voucher();
        voucher.setId(voucherId);

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            service.useVoucher(voucherId, paymentId, userId);
        });
    }

    @Test
    public void testUseVoucherInvalidUserId() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";
        String differentUserId = "user2";

        Voucher voucher = new Voucher();
        voucher.setId(voucherId);

        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setUserId(differentUserId);

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        assertThrows(IllegalArgumentException.class, () -> {
            service.useVoucher(voucherId, paymentId, userId);
        });
    }

    @Test
    public void testUseVoucherVoucherNotAvailable() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";

        Voucher voucher = new Voucher();
        voucher.setId(voucherId);
        voucher.setHasUsageLimit(true);
        voucher.setUsageLimit(0);

        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setUserId(userId);

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        assertThrows(IllegalArgumentException.class, () -> {
            service.useVoucher(voucherId, paymentId, userId);
        });
    }

    @Test
    public void testUseVoucherMinimumOrderNotMet() {
        Long voucherId = 1L;
        Long paymentId = 1L;
        String userId = "user1";

        Voucher voucher = new Voucher();
        voucher.setId(voucherId);
        voucher.setHasUsageLimit(true);
        voucher.setMinimumOrder(1000);

        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setUserId(userId);
        payment.setTotalPrice(500);

        when(voucherRepository.findById(voucherId)).thenReturn(Optional.of(voucher));
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        assertThrows(IllegalArgumentException.class, () -> {
            service.useVoucher(voucherId, paymentId, userId);
        });
    }
}
