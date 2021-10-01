package com.kumarpritam.config;

import com.kumarpritam.activities.CancellationActivity;
import com.kumarpritam.activities.CancellationActivityImpl;
import com.kumarpritam.activities.SubscriptionActivityImpl;
import com.kumarpritam.activities.SubscriptionActivityInterface;
import com.kumarpritam.workflows.CancellationImpl;
import com.kumarpritam.workflows.Shared;
import com.kumarpritam.workflows.SubscriptionWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
public class RootConfig {
    @Autowired private DataSource dataSource;
    @Autowired private RedisConfig redisConfig;
    @Autowired private TemporalConfig temporalConfig;
    @Autowired private SubscriptionActivityImpl subscriptionActivity;
    @Autowired private CancellationActivityImpl cancellationActivity;
    @Bean
    public JdbcTemplate getTemplate(){
        return new JdbcTemplate(dataSource);
    }
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return tomcatServletWebServerFactory -> tomcatServletWebServerFactory.addContextCustomizers((TomcatContextCustomizer) context -> {
            context.setCookieProcessor(new LegacyCookieProcessor());
        });
    }

    @Bean
    public RedissonClient getClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisConfig.getHost() + ":" + redisConfig.getPort());
        return Redisson.create(config);
    }

    @Bean
    public WorkflowServiceStubs getWorkflowService(){
        return WorkflowServiceStubs.newInstance();
    }

    @Bean
    public WorkflowClient getSubscriptionWorkflowClient() {
        WorkflowClient client = WorkflowClient.newInstance(getWorkflowService(), WorkflowClientOptions.newBuilder().setNamespace(temporalConfig.subscriptionTemporalNameSpace).build());
        return client;
    }

    @Bean
    public void register(){
        String[] subQs = temporalConfig.getSubscriptionQueues().split(",");
        String[] canQs = temporalConfig.getCancellationTaskQueues().split(",");
        Worker[] workers = new Worker[subQs.length];
        Worker[] canWorkers = new Worker[canQs.length];
        WorkerFactory factory = WorkerFactory.newInstance(getSubscriptionWorkflowClient());
        for(int i = 0; i < subQs.length; i++){
            workers[i] = factory.newWorker(subQs[i]);
            workers[i].registerActivitiesImplementations(subscriptionActivity);
            workers[i].registerWorkflowImplementationTypes(SubscriptionWorkflowImpl.class);
        }
        for(int i = 0; i < canQs.length; i++){
            canWorkers[i] = factory.newWorker(canQs[i]);
            canWorkers[i].registerActivitiesImplementations(cancellationActivity);
            canWorkers[i].registerWorkflowImplementationTypes(CancellationImpl.class);
        }
        factory.start();
    }
}
