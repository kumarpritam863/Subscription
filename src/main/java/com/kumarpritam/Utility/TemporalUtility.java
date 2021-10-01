package com.kumarpritam.Utility;

import com.kumarpritam.config.TemporalConfig;
import io.temporal.client.WorkflowOptions;
import io.temporal.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemporalUtility {
    @Autowired private TemporalConfig temporalConfig;

    public String getTaskQueue(String userId){
        int index = Integer.parseInt(userId) % 10;
        String[] queues = temporalConfig.getSubscriptionQueues().split(",");
        return queues[index];
    }

    public String getDebitTaskQueue(String userId){
        int index = Integer.parseInt(userId) % 10;
        String[] queues = temporalConfig.getDebitQueues().split(",");
        return queues[index];
    }

    public String getCancellationTaskQueue(String userId){
        int index = Integer.parseInt(userId) % 10;
        String[] queues = temporalConfig.getCancellationTaskQueues().split(",");
        return queues[index];
    }

    public String getWorkflowId(String userId) {
        int index = Integer.parseInt(userId) % 10;
        String[] queues = temporalConfig.getWorkflowIds().split(",");
        return queues[index] + "||" + userId;
    }

    public String getCancellationWorkflowId(String userId){
        int index = Integer.parseInt(userId) % 10;
        String[] queues = temporalConfig.getCancellationWorkflowIds().split(",");
        return queues[index] + "||" + userId;
    }

    public WorkflowOptions getOptions(String userid){
        return WorkflowOptions.newBuilder()
                .setTaskQueue(getTaskQueue(userid))
                .setWorkflowId(getWorkflowId(userid))
                .build();
    }

    public WorkflowOptions getCancellationOptions(String userId){
        return WorkflowOptions.newBuilder()
                .setTaskQueue(getCancellationTaskQueue(userId))
                .setWorkflowId(getCancellationWorkflowId(userId))
                .build();
    }
}
