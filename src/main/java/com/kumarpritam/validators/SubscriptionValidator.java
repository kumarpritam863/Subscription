package com.kumarpritam.validators;

import com.kumarpritam.exceptions.ErrorCode;
import com.kumarpritam.exceptions.SubscriptionValidationException;
import io.temporal.api.workflowservice.v1.*;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionValidator {

    @Autowired WorkflowServiceStubs serviceStubs;
    @Autowired WorkflowClient client;
    private static final String IS_ANY_ACTIVE_SUBSCRIPTION = "WorkflowType = '%s' and WorkflowId = '%s' and ExecutionStatus = 'Running'";

    public void isAnyActiveSubscription(String subscriptionId) throws SubscriptionValidationException {
        String query = String.format(IS_ANY_ACTIVE_SUBSCRIPTION, "subscription.new", subscriptionId);
        System.out.println("Query string is " + query);
        ListWorkflowExecutionsResponse runningWorkflow = getExecutionsResponse(query, client, serviceStubs);
        System.out.println(
                "Currently being processed: " + runningWorkflow.getExecutionsList().size());
        if(!runningWorkflow.getExecutionsList().isEmpty())
            throw new SubscriptionValidationException(ErrorCode.ACTIVE_SUBSCRIPTION_FOUND, "Active subscription found for " + subscriptionId);
    }

    private static ListWorkflowExecutionsResponse getExecutionsResponse(String query, WorkflowClient client, WorkflowServiceStubs service) {
        ListWorkflowExecutionsRequest listWorkflowExecutionRequest =
                ListWorkflowExecutionsRequest.newBuilder()
                        .setNamespace(client.getOptions().getNamespace())
                        .setQuery(query)
                        .build();
        ListWorkflowExecutionsResponse listWorkflowExecutionsResponse =
                service.blockingStub().listWorkflowExecutions(listWorkflowExecutionRequest);
        return listWorkflowExecutionsResponse;
    }
}
