package com.kumarpritam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    private String userId;
    private Long planId;
    private Trial trail;
    private Integer numberOfBillingCycles;
    private Amount billingCharge;
}
