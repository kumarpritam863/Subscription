package com.kumarpritam.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface CancellationActivity {
    @ActivityMethod
    boolean handleCancellation(String workflowId);
    void terminateWorkflow(String workflowId);
    void clearRedis(String workflowId) throws Exception;
}
