
package com.kumarpritam.activities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SubscriptionActivityImpl implements SubscriptionActivityInterface {
    @Autowired RedissionActivities redissionActivities;
    @Override
    public boolean getCancellationStatus(String workflowId) throws Exception {
        return redissionActivities.getCancellationStatus(workflowId);
    }
}

