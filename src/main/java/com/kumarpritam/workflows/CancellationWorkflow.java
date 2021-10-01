package com.kumarpritam.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CancellationWorkflow {

    @WorkflowMethod
    void cancelWorkflow(String userId, String workflowId, int pollCount, String cancellationTaskQueue);
}
