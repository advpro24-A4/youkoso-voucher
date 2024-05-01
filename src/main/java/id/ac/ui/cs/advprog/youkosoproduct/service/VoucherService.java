package id.ac.ui.cs.advprog.youkosoproduct.service;

import java.util.List;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;

public interface VoucherService {
    Voucher create(Voucher voucher);

    void delete(Long id);

    List<Voucher> findAll();

    Voucher findVoucherById(Long id);

    void edit(Long id, String name, double discountPercentage, boolean hasUsageLimit,
            int usageLimit, double minimumOrder, int maximumDiscountAmount);
                
}
