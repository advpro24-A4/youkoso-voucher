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
    public void delete(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucherById(Long voucherId) {
        return voucherRepository.findById(voucherId).orElse(null);
    }

    @Override
    public void edit(Long id, String name, double discountPercentage, boolean hasUsageLimit,
            int usageLimit, double minimumOrder, int maximumDiscountAmount) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            voucher.setName(name);
            voucher.setDiscountPercentage(discountPercentage);
            voucher.setHasUsageLimit(hasUsageLimit);
            voucher.setUsageLimit(usageLimit);
            voucher.setMinimumOrder(minimumOrder);
            voucher.setMaximumDiscountAmount(maximumDiscountAmount);
            voucherRepository.save(voucher);
        } else {
            throw new IllegalArgumentException("There is no voucher with ID " + id);
        }
    }
    
}
