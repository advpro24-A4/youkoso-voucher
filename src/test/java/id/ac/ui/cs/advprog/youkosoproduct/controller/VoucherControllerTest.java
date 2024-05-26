package id.ac.ui.cs.advprog.youkosoproduct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.model.builder.VoucherBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.concurrent.CompletableFuture;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

public class VoucherControllerTest {

    private MockMvc mockMvc;
    private Voucher voucher;
    private Voucher voucherWithDefault;
    private CompletableFuture<AuthResponse> futureAuthResponse;

    @Mock
    private VoucherService voucherService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private VoucherController voucherController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voucherController).build();

        this.futureAuthResponse = CompletableFuture.completedFuture(new AuthResponse());

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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.create(any(Voucher.class))).thenReturn(this.voucherWithDefault);

        MvcResult mvcResult = mockMvc.perform(post("/voucher/api/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voucher)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());

        verify(voucherService, times(1)).create(any(Voucher.class));
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testCreateVoucherBadRequest() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.create(any(Voucher.class)))
                .thenThrow(new IllegalArgumentException("Invalid voucher attribute"));

        MvcResult mvcResult = mockMvc.perform(post("/voucher/api/create")
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voucher)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to create voucher: Invalid voucher attribute"));

        verify(voucherService, times(1)).create(any(Voucher.class));
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindVoucherById() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.findVoucherById(1L)).thenReturn(this.voucher);

        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.findVoucherById(2L))
                .thenThrow(new IllegalArgumentException("There is no voucher with ID " + 2L));

        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read/{id}", 2L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to find voucher by id: There is no voucher with ID 2"));

        verify(voucherService, times(1)).findVoucherById(2L);
        verifyNoMoreInteractions(voucherService);
    }

    @Test
    public void testFindVoucherByIdWithDefaultAttributes() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.findVoucherById(2L)).thenReturn(this.voucherWithDefault);

        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read/{id}", 2L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(this.voucher);
        vouchers.add(this.voucherWithDefault);
    
        when(voucherService.findAll()).thenReturn(vouchers);
    
        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read-all"))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

        List<Voucher> vouchers = new ArrayList<>();
    
        when(voucherService.findAll()).thenReturn(vouchers);
    
        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read-all"))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    
        verify(voucherService, times(1)).findAll();
        verifyNoMoreInteractions(voucherService);
    }
    
    @Test
    public void testFindAllVouchersException() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);
        when(voucherService.findAll()).thenThrow(new RuntimeException("Error retrieving vouchers"));
    
        MvcResult mvcResult = mockMvc.perform(get("/voucher/api/read-all"))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to find all vouchers: Error retrieving vouchers"));
    
        verify(voucherService, times(1)).findAll();
        verifyNoMoreInteractions(voucherService);
    }
    
    @Test
    void testUpdateVoucher() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

        Voucher updatedVoucher = new VoucherBuilder()
                .name("Updated Voucher")
                .discountPercentage(90)
                .hasUsageLimit(true)
                .usageLimit(150)
                .minimumOrder(75)
                .maximumDiscountAmount(150)
                .build();
        updatedVoucher.setId(3L);
    
        MvcResult mvcResult = mockMvc.perform(put("/voucher/api/edit/{voucherId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken")
                .content(asJsonString(updatedVoucher)))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());
    
        verify(voucherService, times(1)).edit(
                eq(1L),
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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

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
    
        MvcResult mvcResult = mockMvc.perform(put("/voucher/api/edit/{voucherId}", 3L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedVoucher)))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to update voucher: There is no voucher with ID 3"));
    
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
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

        MvcResult mvcResult = mockMvc.perform(delete("/voucher/api/delete/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken"))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk());
    
        verify(voucherService, times(1)).delete(1L);
        verifyNoMoreInteractions(voucherService);
    }
    
    @Test
    public void testDeleteNonExistentVoucher() throws Exception {
        when(authService.validateToken(anyString())).thenReturn(this.futureAuthResponse);

        doThrow(new IllegalArgumentException("There is no voucher with ID 3")).when(voucherService).delete(3L);
    
        MvcResult mvcResult = mockMvc.perform(delete("/voucher/api/delete/{id}", 3L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer someToken"))
                .andExpect(request().asyncStarted())
                .andReturn();
    
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to delete voucher: There is no voucher with ID 3"));
    
        verify(voucherService, times(1)).delete(3L);
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
