package com.kumarpritam.entitites;

import com.kumarpritam.models.Amount;
import com.kumarpritam.models.Trial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionEntity {
    private Long id;
    private Long planId;
    private Long accountId;
    private String status;
    private Trial trial;
    private Integer numberOfBillingCycles;
    private Amount billingAmount;
    private Timestamp subscriptionStartedAt;
    private Timestamp subscriptionEndedAt;
    private Timestamp startOfCurrentBillingCycle;
    private Timestamp endOfCurrentBillingCycle;
    private Timestamp nextSubscriptionChargeAt;
    private Long numberOfCyclesBilled;
    private Long totalCyclesPassed;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
