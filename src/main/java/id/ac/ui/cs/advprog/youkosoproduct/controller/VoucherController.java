package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.dto.UseVoucherRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.VoucherListRequest;
import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;
import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.model.builder.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.repository.PaymentRepository;
import id.ac.ui.cs.advprog.youkosoproduct.service.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.scheduling.annotation.Async;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @Async
    @PutMapping("/use/{voucherId}")
    public CompletableFuture<ResponseEntity<?>> useVoucher(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody UseVoucherRequest useVoucherRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                Long voucherId = useVoucherRequest.getVoucherId();
                Long paymentId = useVoucherRequest.getPaymentId();
                voucherService.useVoucher(voucherId, paymentId);

                DefaultResponse<String> response = new DefaultResponseBuilder<String>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data("Voucher used successfully")
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to use voucher: " + e.getMessage());
            }
        });
    }

    @Async
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<?>> voucherListPage(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody VoucherListRequest voucherListRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                if (voucherListRequest.getPaymentId() == null) {
                    throw new BadRequestException("Invalid request");
                }

                List<Voucher> allVouchers = voucherService.findAll();
                DefaultResponse<List<Voucher>> response = new DefaultResponseBuilder<List<Voucher>>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(allVouchers)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in retrieving vouchers information!", e);
                return ResponseEntity.badRequest().body("Failed to retrieve vouchers information: " + e.getMessage());
            }
        });
    }

    @Async
    @PostMapping("/api/create")
    public CompletableFuture<ResponseEntity<?>> createVoucher(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Voucher voucher) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                voucherService.create(voucher);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error("Error in creating voucher!", e);
                return ResponseEntity.badRequest().body("Failed to create voucher: " + e.getMessage());
            }
        });
    }

    @Async
    @GetMapping("/api/read/{id}")
    public CompletableFuture<ResponseEntity<?>> findVoucherById(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                Voucher obtainedVoucher = voucherService.findVoucherById(id);
                return ResponseEntity.ok(obtainedVoucher);
            } catch (Exception e) {
                logger.error("Error in find voucher by id!", e);
                return ResponseEntity.badRequest().body("Failed to find voucher by id: " + e.getMessage());
            }
        });
    }

    @Async
    @GetMapping("/api/read-all")
    public CompletableFuture<ResponseEntity<?>> findAllVouchers() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Voucher> allVouchers = voucherService.findAll();
                return ResponseEntity.ok(allVouchers);
            } catch (Exception e) {
                logger.error("Error in find all vouchers!", e);
                return ResponseEntity.badRequest().body("Failed to find all vouchers: " + e.getMessage());
            }
        });
    }

    @Async
    @PutMapping("/api/edit/{id}")
    public CompletableFuture<ResponseEntity<?>> updateVoucher(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("id") Long id,
            @RequestBody Voucher voucher) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                voucherService.edit(
                        id, voucher.getName(), voucher.getDiscountPercentage(),
                        voucher.getHasUsageLimit(), voucher.getUsageLimit(),
                        voucher.getMinimumOrder(), voucher.getMaximumDiscountAmount());
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error("Error in update voucher!", e);
                return ResponseEntity.badRequest().body("Failed to update voucher: " + e.getMessage());
            }
        });
    }

    @Async
    @DeleteMapping("/api/delete/{id}")
    public CompletableFuture<ResponseEntity<?>> deleteVoucher(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                voucherService.delete(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error("Error in delete voucher!", e);
                return ResponseEntity.badRequest().body("Failed to delete voucher: " + e.getMessage());
            }
        });
    }
}
