
package com.kumarpritam.activities;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface SubscriptionActivityInterface {
    @ActivityMethod
    boolean getCancellationStatus(String workflowId) throws Exception;
}

