package com.kumarpritam.results;

import com.kumarpritam.receipts.PaymentReceipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResult {
    private String customerId;
    private String accountId;
    private String subscriptionId;
    private String planId;
    private String status;
    private Timestamp subscriptionStartedAt;
    private Timestamp subscriptionEndedAt;
    private Timestamp startOfCurrentBillingCycle;
    private Timestamp endOfCurrentBillingCycle;
    private Timestamp nextSubscriptionChargeAt;
    private Long numberOfCyclesBilled;
    private Long totalCyclesPassed;
    private Timestamp createdAt;
    private PaymentReceipt payment;
}
