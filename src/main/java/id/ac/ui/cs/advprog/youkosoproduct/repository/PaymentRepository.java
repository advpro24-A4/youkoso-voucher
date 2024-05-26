package id.ac.ui.cs.advprog.youkosoproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.youkosoproduct.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}