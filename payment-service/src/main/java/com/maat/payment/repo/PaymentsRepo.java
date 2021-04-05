package com.maat.payment.repo;

import com.maat.payment.beans.PaymentDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepo extends CrudRepository<PaymentDetail, Long> {
//    PaymentDetail findByUsername(String username);
}
