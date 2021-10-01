package com.kumarpritam.workflows;
import com.kumarpritam.models.Subscription;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SubscriptionWorkflow {
    @WorkflowMethod(name = "subscription.new")
    void startSubscription(Subscription subscription, String workflowId, String debitTaskQueue);

    @SignalMethod
    void cancelSubscription(String workflowId) throws Exception;
}
