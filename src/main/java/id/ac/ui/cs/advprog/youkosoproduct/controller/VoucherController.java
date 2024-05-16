package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    @Async
    @PutMapping("/{voucherId}")
    public CompletableFuture<ResponseEntity<?>> useVoucher(@PathVariable("voucherId") Long voucherId,
            @ModelAttribute Voucher voucherToUse) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // do something
                return ResponseEntity.ok("Voucher used successfully");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to use voucher: " + e.getMessage());
            }
        });
    }

    @Async
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<?>> voucherListPage() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Voucher> allVouchers = voucherService.findAll();
                return ResponseEntity.ok(allVouchers);
            } catch (Exception e) {
                logger.error("Error in retrieving vouchers information!", e);
                return ResponseEntity.badRequest().body("Failed to retrieve vouchers information: " + e.getMessage());
            }
        });
    }

    @Async
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> getVoucherDetail(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Voucher obtainedVoucher = voucherService.findVoucherById(id);
                return ResponseEntity.ok(obtainedVoucher);
            } catch (Exception e) {
                logger.error("Error in retrieving voucher information!", e);
                return ResponseEntity.badRequest().body("Failed to retrieve voucher information: " + e.getMessage());
            }
        });
    }

    @Async
    @PostMapping("/api/create")
    public CompletableFuture<ResponseEntity<?>> createVoucher(@RequestBody Voucher voucher) {
        return CompletableFuture.supplyAsync(() -> {
            try {
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
    public CompletableFuture<ResponseEntity<?>> findVoucherById(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
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
    public CompletableFuture<ResponseEntity<?>> updateVoucher(@PathVariable("id") Long id,
            @RequestBody Voucher voucher) {
        return CompletableFuture.supplyAsync(() -> {
            try {
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
    public CompletableFuture<ResponseEntity<?>> deleteVoucher(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                voucherService.delete(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                logger.error("Error in delete voucher!", e);
                return ResponseEntity.badRequest().body("Failed to delete voucher: " + e.getMessage());
            }
        });
    }
}
