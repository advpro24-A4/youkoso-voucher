package id.ac.ui.cs.advprog.youkosoproduct.repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

}