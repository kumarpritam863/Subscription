package com.kumarpritam.services;

import com.kumarpritam.Utility.TemporalUtility;
import com.kumarpritam.activities.CancellationActivity;
import com.kumarpritam.activities.RedissionActivities;
import com.kumarpritam.activities.SubscriptionActivityImpl;
import com.kumarpritam.config.TemporalConfig;
import com.kumarpritam.models.Subscription;
import com.kumarpritam.results.SubscriptionResult;
import com.kumarpritam.validators.SubscriptionValidator;
import com.kumarpritam.workflows.CancellationImpl;
import com.kumarpritam.workflows.CancellationWorkflow;
import com.kumarpritam.workflows.SubscriptionWorkflow;
import com.kumarpritam.workflows.SubscriptionWorkflowImpl;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SubscriptionService {

    private Logger log = LoggerFactory.getLogger(SubscriptionService.class.getName());

    @Autowired private SubscriptionValidator subscriptionValidator;
    @Autowired private SubscriptionActivityImpl subscriptionActivity;
    @Autowired private RedissionActivities redissionActivities;
    @Autowired private WorkflowServiceStubs service;
    @Autowired private WorkflowClient workflowClient;
    @Autowired private TemporalUtility temporalUtility;
    @Autowired private CancellationActivity activity;
    @Autowired private TemporalConfig config;

    public SubscriptionResult startSubscription(Subscription subscription) throws Exception {
        WorkflowOptions options = temporalUtility.getOptions(subscription.getUserId());
        SubscriptionWorkflow subscriptionWorkflow = workflowClient.newWorkflowStub(SubscriptionWorkflow.class, options);
        subscriptionValidator.isAnyActiveSubscription(temporalUtility.getWorkflowId(subscription.getUserId()));
        WorkflowExecution we = WorkflowClient.start(subscriptionWorkflow::startSubscription, subscription, temporalUtility.getWorkflowId(subscription.getUserId()), temporalUtility.getDebitTaskQueue(subscription.getUserId()));
        return SubscriptionResult.builder().subscriptionId(we.getWorkflowId()).build();
    }

    public void cancelSubscription(String subscriptionId, String userId) throws Exception {
        //redissionActivities.setCancellationStatus(subscriptionId);
        WorkflowClient client = WorkflowClient.newInstance(service, WorkflowClientOptions.newBuilder().setNamespace(config.getSubscriptionTemporalNameSpace()).build());
        SubscriptionWorkflow subscriptionWorkflow = client.newWorkflowStub(SubscriptionWorkflow.class, subscriptionId);
        subscriptionWorkflow.cancelSubscription(subscriptionId);
        WorkflowOptions options = temporalUtility.getCancellationOptions(userId);
        CancellationWorkflow workflow = workflowClient.newWorkflowStub(CancellationWorkflow.class, options);
        WorkflowClient.start(workflow::cancelWorkflow, userId, subscriptionId, 3, temporalUtility.getCancellationTaskQueue(userId));
    }

    public void terminate(String subscriptionId) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service, WorkflowClientOptions.newBuilder().setNamespace("Subscription").build());
        WorkflowStub wfs =
                client.newUntypedWorkflowStub(
                        subscriptionId, Optional.empty(), Optional.of("subscription.new"));
        wfs.terminate("Termination Reason");
    }
}
