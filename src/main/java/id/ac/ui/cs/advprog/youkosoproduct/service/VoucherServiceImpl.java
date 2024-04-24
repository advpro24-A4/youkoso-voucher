package id.ac.ui.cs.advprog.youkosoproduct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.repository.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Voucher create(Voucher voucher) {
        return null;
    }

    @Override
    public void delete(String voucherId) {
        return;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher findVoucherById(String voucherId) {
        return null;
    }

    @Override
    public void edit(String voucherId, String voucherName, double voucherDiscountAmount, int voucherMaxUsage) {
        return;
    }

}
