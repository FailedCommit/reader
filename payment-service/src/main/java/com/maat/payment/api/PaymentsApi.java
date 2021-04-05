package com.maat.payment.api;

import com.maat.payment.service.PaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentsApi {
  private final PaymentsService paymentsService;

}


