package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.dto.UseVoucherRequest;
import id.ac.ui.cs.advprog.youkosoproduct.dto.VoucherListResponse;
import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;
import id.ac.ui.cs.advprog.youkosoproduct.utils.AuthResponse;
import id.ac.ui.cs.advprog.youkosoproduct.utils.AuthService;
import id.ac.ui.cs.advprog.youkosoproduct.utils.DefaultResponse;
import id.ac.ui.cs.advprog.youkosoproduct.utils.DefaultResponseBuilder;
import id.ac.ui.cs.advprog.youkosoproduct.utils.DefaultResponseWithData;
import id.ac.ui.cs.advprog.youkosoproduct.utils.DefaultResponseWithDataBuilder;

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
    @PutMapping("list/{paymentId}")
    public CompletableFuture<ResponseEntity<?>> useVoucher(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("paymentId") Long paymentId,
            @RequestBody UseVoucherRequest useVoucherRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                AuthResponse authResponse = authService.validateToken(authHeader).join();
                if (authResponse == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                Long voucherId = useVoucherRequest.getVoucherId();
                // String userId = authResponse.getUser().getId();
                // voucherService.useVoucher(voucherId, paymentId, userId);
                voucherService.useVoucher(voucherId, paymentId);

                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to use voucher: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
        });
    }

    @Async
    @GetMapping("/list/{paymentId}")
    public CompletableFuture<ResponseEntity<?>> voucherListPage(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @PathVariable("paymentId") Long paymentId) {
        return CompletableFuture.supplyAsync(() -> {
            
            AuthResponse authResponse = authService.validateToken(authHeader).join();
            if (authResponse == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            try {
                List<Voucher> allVouchers = voucherService.findAll();
                if (paymentId == null) {
                    throw new BadRequestException("Invalid request");
                }
                
                VoucherListResponse response = new VoucherListResponse();
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Success");
                response.setSuccess(true);
                response.setVoucherData(allVouchers);
                response.setPaymentId(paymentId);
                return ResponseEntity.ok(response);

            } catch (Exception e) {
                logger.error("Error in retrieving vouchers information!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to retrieve vouchers information: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
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
                Voucher createdVoucher = voucherService.findVoucherById(voucher.getId());
                DefaultResponseWithData<Voucher> response = new DefaultResponseWithDataBuilder<Voucher>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(createdVoucher)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in creating voucher!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to create voucher: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
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
                DefaultResponseWithData<Voucher> response = new DefaultResponseWithDataBuilder<Voucher>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(obtainedVoucher)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in find voucher by id!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to find voucher by id: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
        });
    }

    @Async
    @GetMapping("/api/read-all")
    public CompletableFuture<ResponseEntity<?>> findAllVouchers() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Voucher> allVouchers = voucherService.findAll();
                DefaultResponseWithData<List<Voucher>> response = new DefaultResponseWithDataBuilder<List<Voucher>>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(allVouchers)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in find all vouchers!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to find all vouchers: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
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
                Voucher editedVoucher = voucherService.findVoucherById(voucher.getId());

                DefaultResponseWithData<Voucher> response = new DefaultResponseWithDataBuilder<Voucher>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(editedVoucher)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in update voucher!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to update voucher: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
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

                Voucher deletedVoucher = voucherService.findVoucherById(id);
                voucherService.delete(id);
                DefaultResponseWithData<Voucher> response = new DefaultResponseWithDataBuilder<Voucher>()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success")
                        .success(true)
                        .data(deletedVoucher)
                        .build();
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error in delete voucher!", e);
                DefaultResponse response = new DefaultResponseBuilder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Failed to delete voucher: " + e.getMessage())
                        .success(false)
                        .build();
                return ResponseEntity.badRequest().body(response);
            }
        });
    }
}
