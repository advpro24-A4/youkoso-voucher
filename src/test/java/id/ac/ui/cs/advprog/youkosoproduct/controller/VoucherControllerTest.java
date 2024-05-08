package id.ac.ui.cs.advprog.youkosoproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.model.VoucherBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VoucherControllerTest {

    private MockMvc mockMvc;
    private Voucher voucher;
    private Voucher voucherWithDefault;

    @Mock
    private VoucherService voucherService;

    @InjectMocks
    private VoucherController voucherController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voucherController).build();

        this.voucher = new VoucherBuilder()
                .name("Voucher 1")
                .discountPercentage(50)
                .hasUsageLimit(true)
                .usageLimit(100)
                .minimumOrder(50000)
                .maximumDiscountAmount(25000)
                .build();
        this.voucher.setId(1L);

        this.voucherWithDefault = new VoucherBuilder()
                .name("Voucher 2")
                .discountPercentage(20)
                .hasUsageLimit(false)
                .build();
        this.voucherWithDefault.setId(2L);
    }

    @Test
    public void testCreateVoucher() throws Exception {
        when(voucherService.create(any(Voucher.class))).thenReturn(this.voucherWithDefault);

        mockMvc.perform(post("/voucher/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voucher)))
                .andExpect(status().isOk());

        verify(voucherService, times(1)).create(any(Voucher.class));
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testCreateVoucherBadRequest() throws Exception {
        when(voucherService.create(any(Voucher.class)))
                .thenThrow(new IllegalArgumentException("Invalid voucher attribute"));

        mockMvc.perform(post("/voucher/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voucher)))
                .andExpect(status().isBadRequest());

        verify(voucherService, times(1)).create(any(Voucher.class));
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindVoucherById() throws Exception {
        when(voucherService.findVoucherById(1L)).thenReturn(this.voucher);

        mockMvc.perform(get("/voucher/api/read/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Voucher 1"))
                .andExpect(jsonPath("$.discountPercentage").value(50))
                .andExpect(jsonPath("$.hasUsageLimit").value(true))
                .andExpect(jsonPath("$.usageLimit").value(100))
                .andExpect(jsonPath("$.minimumOrder").value(50000))
                .andExpect(jsonPath("$.maximumDiscountAmount").value(25000));

        verify(voucherService, times(1)).findVoucherById(1L);
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindVoucherByIdIfNotFound() throws Exception {
        when(voucherService.findVoucherById(2L))
                .thenThrow(new IllegalArgumentException("There is no voucher with ID " + 2L));

        mockMvc.perform(get("/voucher/api/read/{id}", 2L))
                .andExpect(status().isBadRequest());

        verify(voucherService, times(1)).findVoucherById(2L);
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindVoucherByIdWithDefaultAttributes() throws Exception {
        when(voucherService.findVoucherById(2L)).thenReturn(this.voucherWithDefault);

        mockMvc.perform(get("/voucher/api/read/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Voucher 2"))
                .andExpect(jsonPath("$.discountPercentage").value(20))
                .andExpect(jsonPath("$.hasUsageLimit").value(false))
                .andExpect(jsonPath("$.usageLimit").value(Integer.MAX_VALUE))
                .andExpect(jsonPath("$.minimumOrder").value(0))
                .andExpect(jsonPath("$.maximumDiscountAmount").value(Integer.MAX_VALUE));

        verify(voucherService, times(1)).findVoucherById(2L);
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindAllVouchers() throws Exception {
        List<Voucher> vouchers = new ArrayList<Voucher>();

        vouchers.add(this.voucher);
        vouchers.add(this.voucherWithDefault);

        when(voucherService.findAll()).thenReturn(vouchers);

        mockMvc.perform(get("/voucher/api/read-all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Voucher 1"))
                .andExpect(jsonPath("$[0].discountPercentage").value(50))
                .andExpect(jsonPath("$[0].hasUsageLimit").value(true))
                .andExpect(jsonPath("$[0].usageLimit").value(100))
                .andExpect(jsonPath("$[0].minimumOrder").value(50000))
                .andExpect(jsonPath("$[0].maximumDiscountAmount").value(25000))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Voucher 2"))
                .andExpect(jsonPath("$[1].discountPercentage").value(20))
                .andExpect(jsonPath("$[1].hasUsageLimit").value(false))
                .andExpect(jsonPath("$[1].usageLimit").value(Integer.MAX_VALUE))
                .andExpect(jsonPath("$[1].minimumOrder").value(0))
                .andExpect(jsonPath("$[1].maximumDiscountAmount").value(Integer.MAX_VALUE));

        verify(voucherService, times(1)).findAll();
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindAllVouchersIfEmpty() throws Exception {
        List<Voucher> vouchers = new ArrayList<Voucher>();

        when(voucherService.findAll()).thenReturn(vouchers);

        mockMvc.perform(get("/voucher/api/read-all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(voucherService, times(1)).findAll();
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    void testUpdateVoucher() throws Exception {
        Voucher updatedVoucher = new VoucherBuilder()
                .name("Updated Voucher")
                .discountPercentage(90)
                .hasUsageLimit(true)
                .usageLimit(150)
                .minimumOrder(75)
                .maximumDiscountAmount(150)
                .build();
        updatedVoucher.setId(3L);

        mockMvc.perform(put("/voucher/api/edit/{voucherId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedVoucher)))
                .andExpect(status().isOk());

        verify(voucherService, times(1)).edit(
                eq(3L),
                eq("Updated Voucher"),
                eq(90),
                eq(true),
                eq(150),
                eq(75),
                eq(150));

        verifyNoMoreInteractions(voucherService);
    }

    @Test
    void testUpdateVoucherVoucherNotFound() throws Exception {
        Voucher updatedVoucher = new VoucherBuilder()
                .name("Updated Voucher")
                .discountPercentage(90)
                .hasUsageLimit(true)
                .usageLimit(150)
                .minimumOrder(75)
                .maximumDiscountAmount(150)
                .build();
        updatedVoucher.setId(3L);

        doThrow(new IllegalArgumentException("There is no voucher with ID " + updatedVoucher.getId()))
                .when(voucherService).edit(
                        eq(3L),
                        eq("Updated Voucher"),
                        eq(90),
                        eq(true),
                        eq(150),
                        eq(75),
                        eq(150));

        mockMvc.perform(put("/voucher/api/edit/{voucherId}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedVoucher)))
                .andExpect(status().isBadRequest());

        verify(voucherService, times(1)).edit(
                eq(3L),
                eq("Updated Voucher"),
                eq(90),
                eq(true),
                eq(150),
                eq(75),
                eq(150));

        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testDeleteVoucher() throws Exception {
        mockMvc.perform(delete("/voucher/api/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(voucherService, times(1)).delete(1L);
        verifyNoMoreInteractions(voucherService);
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}