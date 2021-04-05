package com.maat.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.maat.payment.beans")
public class PaymentServiceLauncher {
  public static void main(String[] args) {
    SpringApplication.run(PaymentServiceLauncher.class, args);
  }
}
