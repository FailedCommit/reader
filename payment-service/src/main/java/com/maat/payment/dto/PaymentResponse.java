package com.maat.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maat.payment.beans.PaymentDetail;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private PaymentDetail payment;
}
