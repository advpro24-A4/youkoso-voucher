package id.ac.ui.cs.advprog.youkosoproduct.model.builder;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;

public class VoucherBuilder {
    private String name;
    private int discountPercentage;
    private Boolean hasUsageLimit;
    private int usageLimit = Integer.MAX_VALUE;
    private int minimumOrder = 0;
    private int maximumDiscountAmount = Integer.MAX_VALUE;

    public VoucherBuilder name(String name) {
        this.name = name;
        return this;
    }

    public VoucherBuilder discountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public VoucherBuilder hasUsageLimit(Boolean hasUsageLimit) {
        this.hasUsageLimit = hasUsageLimit;
        return this;
    }

    public VoucherBuilder usageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
        return this;
    }

    public VoucherBuilder minimumOrder(int minimumOrder) {
        this.minimumOrder = minimumOrder;
        return this;
    }

    public VoucherBuilder maximumDiscountAmount(int maximumDiscountAmount) {
        this.maximumDiscountAmount = maximumDiscountAmount;
        return this;
    }

    public Voucher build() {
        Voucher voucher = new Voucher();
        voucher.setName(this.name);
        voucher.setDiscountPercentage(this.discountPercentage);
        voucher.setHasUsageLimit(this.hasUsageLimit);
        voucher.setUsageLimit(this.usageLimit);
        voucher.setMinimumOrder(this.minimumOrder);
        voucher.setMaximumDiscountAmount(this.maximumDiscountAmount);
        return voucher;
    }
}
