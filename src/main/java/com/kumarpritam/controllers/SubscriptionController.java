package com.kumarpritam.controllers;

import com.kumarpritam.exceptions.SubscriptionValidationException;
import com.kumarpritam.models.Subscription;
import com.kumarpritam.results.SubscriptionResult;
import com.kumarpritam.services.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping(value = "/v1/subscription")
public class SubscriptionController {

    private Logger log = LoggerFactory.getLogger(SubscriptionController.class.getName());

    @Autowired private SubscriptionService subscriptionService;


    @RequestMapping(value = "/start", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public SubscriptionResult startSubscription(@RequestBody Subscription subscription) throws Exception {
        return subscriptionService.startSubscription(subscription);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity cancelSubscription(@RequestParam(value = "subscriptionId") String subscriptionId, @RequestParam(value = "userId") String userId) throws Exception {
        System.out.println("SubscriptionId is " + subscriptionId);
        subscriptionService.cancelSubscription(subscriptionId, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/terminate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity terminateSubscription(@RequestParam(value = "subscriptionId") String subscriptionId) throws Exception {
        System.out.println("SubscriptionId is " + subscriptionId);
        subscriptionService.terminate(subscriptionId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
