package id.ac.ui.cs.advprog.youkosoproduct.service;

import java.util.List;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;

public interface VoucherService {
    Voucher create(Voucher voucher);

    void delete(String voucherId);

    List<Voucher> findAll();

    Voucher findVoucherById(String voucherId);

    void edit(String voucherId, String voucherName, double voucherDiscountAmount, int voucherMaxUsage);
}
