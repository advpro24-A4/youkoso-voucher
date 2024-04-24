package id.ac.ui.cs.advprog.youkosoproduct.controller;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/list")
    public String voucherListPage(Model model) {
        return "";
    }

    @PutMapping("/{voucherId}")
    public String useVoucher(@PathVariable("id") String id, @ModelAttribute Voucher voucherToUse) {
        return "";
    }
}
