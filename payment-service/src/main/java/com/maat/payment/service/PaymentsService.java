package com.maat.payment.service;

import com.maat.payment.beans.PaymentDetail;
import com.maat.payment.dto.PaymentRequest;

public interface PaymentsService {
    PaymentDetail makePayment(PaymentRequest requeqst);
}