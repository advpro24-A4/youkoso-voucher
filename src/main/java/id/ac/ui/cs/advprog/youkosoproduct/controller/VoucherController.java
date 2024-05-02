package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PutMapping("/{voucherId}")
    public String useVoucher(@PathVariable("id") Long id, @ModelAttribute Voucher voucherToUse) {
        return "";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String voucherListPage() {
        @SuppressWarnings("rawtypes")
        ResponseEntity responseEntity = null;

        try {
            List<Voucher> allVouchers = voucherService.findAll();
            responseEntity = ResponseEntity.ok(allVouchers);
        } catch (Exception e) {
            System.out.println("Error in retrieving vouchers information!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "voucher_list_page";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getVoucherDetail(@PathVariable("id") Long id) {
        @SuppressWarnings("rawtypes")
        ResponseEntity responseEntity = null;

        try {
            Voucher obtainedVoucher = voucherService.findVoucherById(id);
            responseEntity = ResponseEntity.ok(obtainedVoucher);
        } catch (Exception e) {
            System.out.println("Error in retrieving voucher information!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return "voucher_page";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/create", method = RequestMethod.POST)
    public ResponseEntity createVoucher(@RequestBody Voucher voucher) {
        ResponseEntity responseEntity = null;

        try {
            voucherService.create(voucher);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error in creating voucher!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/read/{id}", method = RequestMethod.GET)
    public ResponseEntity findVoucherById(@PathVariable("id") Long id) {
        ResponseEntity responseEntity = null;

        try {
            Voucher obtainedVoucher = voucherService.findVoucherById(id);
            responseEntity = ResponseEntity.ok(obtainedVoucher);
        } catch (Exception e) {
            System.out.println("Error in find voucher by id!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/read-all", method = RequestMethod.GET)
    public ResponseEntity findAllVouchers() {
        ResponseEntity responseEntity = null;

        try {
            List<Voucher> allVouchers = voucherService.findAll();
            responseEntity = ResponseEntity.ok(allVouchers);
        } catch (Exception e) {
            System.out.println("Error in find all vouchers!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateVoucher(@RequestBody Voucher voucher) {
        ResponseEntity responseEntity = null;

        try {
            voucherService.edit(
                    voucher.getId(), voucher.getName(), voucher.getDiscountPercentage(),
                    voucher.getHasUsageLimit(), voucher.getUsageLimit(),
                    voucher.getMinimumOrder(), voucher.getMaximumDiscountAmount());
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error in update voucher!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteVoucher(@PathVariable("id") Long id) {
        ResponseEntity responseEntity = null;

        try {
            voucherService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error in delete voucher!");
            responseEntity = ResponseEntity.badRequest().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}