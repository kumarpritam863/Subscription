package com.kumarpritam.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "com.kumarpritam.config.temporalconfig")
public class TemporalConfig {
    String subscriptionQueues;
    String debitQueues;
    String cancellationTaskQueues;
    String workflowIds;
    String cancellationWorkflowIds;
    String subscriptionTemporalNameSpace;
}
