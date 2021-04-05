package com.maat.payment.service;

import com.maat.payment.repo.PaymentsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService {
    private final PaymentsRepo paymentsRepo;
}
