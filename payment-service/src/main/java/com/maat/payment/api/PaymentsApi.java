package com.maat.payment.api;

import com.maat.payment.beans.PaymentDetail;
import com.maat.payment.dto.PaymentRequest;
import com.maat.payment.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsApi {
  private final PaymentsService paymentsService;

  @PostMapping()
  public PaymentDetail makePayment(@RequestBody PaymentRequest request) {
    return paymentsService.makePayment(request);
  }

  @GetMapping("/hi")
  public PaymentRequest hi(@RequestBody PaymentRequest request) {
    return request;
  }
}
