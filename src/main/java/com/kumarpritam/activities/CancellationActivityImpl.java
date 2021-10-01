package com.kumarpritam.activities;

import com.kumarpritam.config.TemporalConfig;
import com.kumarpritam.exceptions.SubscriptionValidationException;
import com.kumarpritam.validators.SubscriptionValidator;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancellationActivityImpl implements CancellationActivity {
    @Autowired private SubscriptionValidator validator;
    @Autowired private WorkflowServiceStubs service;
    @Autowired private TemporalConfig temporalConfig;
    @Autowired private RedissionActivities redissionActivities;
    @Override
    public boolean handleCancellation(String workflowId) {
        try{
            validator.isAnyActiveSubscription(workflowId);
        } catch (SubscriptionValidationException subscriptionValidationException){
            System.out.println("Subscription with workflowId " + workflowId + " is running");
            return false;
        }
        return true;
    }

    @Override
    public void terminateWorkflow(String workflowId) {
        WorkflowClient client = WorkflowClient.newInstance(service, WorkflowClientOptions.newBuilder().setNamespace(temporalConfig.getSubscriptionTemporalNameSpace()).build());
        WorkflowStub wfs =
                client.newUntypedWorkflowStub(
                        workflowId, Optional.empty(), Optional.of("subscription.new"));
        wfs.terminate("Termination as workflow seems to be stuck");
    }

    @Override
    public void clearRedis(String workflowId) throws Exception {
        redissionActivities.clearRedis(workflowId);
    }
}
