package com.maat.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.payment.beans.Address;
import com.maat.payment.beans.PaymentDetail;
import com.maat.payment.beans.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {
    private User payer;
    private User payee;
    private Address billingAddress;


    public PaymentDetail createPaymentDetails() {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setPayer(payer);
        paymentDetail.setPayee(payee);
        paymentDetail.setBillingAddress(billingAddress);
        return paymentDetail;
    }
}
