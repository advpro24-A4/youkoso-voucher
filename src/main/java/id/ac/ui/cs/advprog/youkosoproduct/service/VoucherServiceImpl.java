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
        if (!voucher.isValid())
            throw new IllegalArgumentException("Invalid voucher attribute");

        return voucherRepository.save(voucher);
    }

    @Override
    public void delete(Long voucherId) {
        if (!voucherRepository.existsById(voucherId)) 
            throw new IllegalArgumentException("There is no voucher with ID " + voucherId);
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucherById(Long id) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (!optionalVoucher.isPresent())
            throw new IllegalArgumentException("There is no voucher with ID " + id);

        return optionalVoucher.get();
    }

    @Override
    public void edit(Long id, String name, int discountPercentage, boolean hasUsageLimit,
            int usageLimit, int minimumOrder, int maximumDiscountAmount) {

        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (!optionalVoucher.isPresent())
            throw new IllegalArgumentException("There is no voucher with ID " + id);

        Voucher voucher = optionalVoucher.get();
        
        Voucher editedVoucher = new Voucher();
        editedVoucher.setName(name);
        editedVoucher.setDiscountPercentage(discountPercentage);
        editedVoucher.setHasUsageLimit(hasUsageLimit);
        editedVoucher.setUsageLimit(usageLimit);
        editedVoucher.setMinimumOrder(minimumOrder);
        editedVoucher.setMaximumDiscountAmount(maximumDiscountAmount);

        if (!editedVoucher.isValid()) 
            throw new IllegalArgumentException("Invalid voucher's attribute(s)");
        
        voucher.setName(name);
        voucher.setDiscountPercentage(discountPercentage);
        voucher.setHasUsageLimit(hasUsageLimit);
        voucher.setUsageLimit(usageLimit);
        voucher.setMinimumOrder(minimumOrder);
        voucher.setMaximumDiscountAmount(maximumDiscountAmount);

        voucherRepository.save(voucher);
    }
}
