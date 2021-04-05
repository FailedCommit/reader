package com.maat.payment.beans;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "payerId", nullable = false)
    private User payer;
    @OneToOne
    @JoinColumn(name = "payeeId", nullable = false)
    private User payee;
    @OneToOne
    @JoinColumn(name = "billingAddressId", nullable = false)
    private Address billingAddress;

//    @OneToOne
//    @JoinColumn(name = "payerId", nullable = false)
//    public User getPayer() {
//        return payer;
//    }
//
//    @OneToOne
//    @JoinColumn(name = "payeeId", nullable = false)
//    public User getPayee() {
//        return payee;
//    }
//
//    @OneToOne
//    @JoinColumn(name = "billingAddressId", nullable = false)
//    public Address getBillingAddress() {
//        return billingAddress;
//    }
}
