package id.ac.ui.cs.advprog.youkosoproduct.dto;

import java.util.List;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import id.ac.ui.cs.advprog.youkosoproduct.utils.DefaultResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherListResponse extends DefaultResponse{
    private List<Voucher> voucherData;
    private Long paymentId;
}