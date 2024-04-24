package id.ac.ui.cs.advprog.youkosoproduct.service;

import java.util.List;
import java.util.Optional;

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
        return voucherRepository.save(voucher);
    }

    @Override
    public void delete(String voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucherById(String voucherId) {
        return voucherRepository.findById(voucherId).orElse(null);
    }

    @Override
    public void edit(String voucherId, String voucherName,
            double voucherDiscountAmount, int voucherMaxUsage) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            voucher.setName(voucherName);
            voucher.setDiscountAmount(voucherDiscountAmount);
            voucher.setMaxUsage(voucherMaxUsage);
            voucherRepository.save(voucher);
        } else {
            throw new IllegalArgumentException("There is no voucher with ID " + voucherId);
        }
    }

}
