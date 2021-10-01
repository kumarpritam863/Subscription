package com.kumarpritam.workflows;

import com.kumarpritam.Utility.TemporalUtility;
import com.kumarpritam.activities.CancellationActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.workflow.Workflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CancellationImpl implements CancellationWorkflow {
    private int numOfPolls;
    @Override
    public void cancelWorkflow(String userId, String workflowId, int pollCount, String cancellationTaskQueue) {
        this.numOfPolls = pollCount;
        CancellationActivity activity = Workflow.newActivityStub(CancellationActivity.class, ActivityOptions.newBuilder().setTaskQueue(cancellationTaskQueue).setScheduleToCloseTimeout(Duration.ofMinutes(5)).setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build()).build());
        while(this.numOfPolls-- > 0){
            if(activity.handleCancellation(workflowId)) {
                /*try{
                    activity.clearRedis(workflowId);
                } catch (Exception ex){
                    System.out.println("Got exception while clearing redis. WARN :: you are fucked");
                }*/
                return;
            }
            Workflow.sleep(Duration.ofSeconds(2));
        }
        activity.terminateWorkflow(workflowId);
        /*try{
            activity.clearRedis(workflowId);
        } catch (Exception ex){
            System.out.println("Got exception while clearing redis. WARN :: you are fucked");
        }*/
    }
}
