package com.maat.payment.service;

import com.maat.payment.beans.Address;
import com.maat.payment.beans.PaymentDetail;
import com.maat.payment.beans.User;
import com.maat.payment.dto.PaymentRequest;
import com.maat.payment.repo.PaymentsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
    private final PaymentsRepo paymentsRepo;

    @Override
    @Transactional
    public PaymentDetail makePayment(PaymentRequest request) {
        PaymentDetail payment = request.createPaymentDetails();
        final User payee = payment.getPayee();
        final User payer = payment.getPayer();
        final Address billingAddress = payment.getBillingAddress();

        return paymentsRepo.save(payment);
    }
}
