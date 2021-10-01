package com.kumarpritam.workflows;
import com.kumarpritam.activities.SubscriptionActivityInterface;
import com.kumarpritam.models.Subscription;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.ActivityStub;
import io.temporal.workflow.Workflow;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SubscriptionWorkflowImpl implements SubscriptionWorkflow {
    private Boolean isSubscriptionCancelled = false;
    private SubscriptionWorkflow continueAsNew;

    @Override
    public void startSubscription(Subscription subscription, String workflowId, String debitTaskQueue) {
        int currentBillingPeriod = subscription.getNumberOfBillingCycles();
        while(currentBillingPeriod-- > 0){
            System.out.println("Sending T-2 notif");
            Workflow.await(Duration.ofSeconds(10), () -> isSubscriptionCancelled == true);
            System.out.println("value of isSubscriptionCancelled = " + isSubscriptionCancelled);
            if(isSubscriptionCancelled){
                System.out.println("Subscription cancelled for user " + subscription.getUserId() + " for workflow " + workflowId);
                return;
            }
            System.out.println("The value of isSubscriptionCancelled for workflow " + workflowId + " is = " + isSubscriptionCancelled);
            ActivityOptions debitActivityOptions = ActivityOptions.newBuilder()
                    .setTaskQueue(debitTaskQueue).setScheduleToCloseTimeout(Duration.ofHours(5))
                    .setStartToCloseTimeout(Duration.ofHours(5))
                    .setHeartbeatTimeout(Duration.ofSeconds(1))
                    .build();
            ActivityStub debitActivityStub = Workflow.newUntypedActivityStub(debitActivityOptions);
            System.out.println("calling debit");
            String debitRes = debitActivityStub.execute("debitUserForCurrentBillingCycle", String.class, subscription.getUserId());
            Workflow.sleep(Duration.ofSeconds(10));
            System.out.println("got debit res for user " + subscription.getUserId() + " Result is :: " + debitRes);
        }
        if(!isSubscriptionCancelled){
            continueAsNew = Workflow.newContinueAsNewStub(SubscriptionWorkflow.class);
            continueAsNew.startSubscription(subscription, workflowId, debitTaskQueue);
        }
    }

    @Override
    public void cancelSubscription(String workflowId) throws Exception {
        System.out.println("got cancellation request for workflowId {}" + workflowId);
        this.isSubscriptionCancelled = true;
    }
}
