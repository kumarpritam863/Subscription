package com.kumarpritam.controllers;

import com.google.common.collect.ImmutableMap;
import com.kumarpritam.enums.PlanStatus;
import com.kumarpritam.exceptions.DuplicacyException;
import com.kumarpritam.exceptions.PlanValidationException;
import com.kumarpritam.models.Plan;
import com.kumarpritam.responses.PlanResponse;
import com.kumarpritam.services.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/v1/plan")
public class PlanController {

    private Logger log = LoggerFactory.getLogger(PlanController.class.getName());

    @Autowired private PlanService planService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PlanResponse createPlan(@RequestBody Plan plan) {
        try {
            return planService.createPlan(plan);
        } catch (PlanValidationException planValidationException){
            return PlanResponse.builder().id(null).uniquePlanId(plan.getUniquePlanId()).status(PlanStatus.CREATION_FAILED).message(ImmutableMap.<String, Object>of("error", planValidationException.errorCode.name(), "reason", planValidationException.getMessage())).build();
        }catch (DuplicacyException duplicacyException){
            return PlanResponse.builder().id(null).uniquePlanId(plan.getUniquePlanId()).status(PlanStatus.CREATION_FAILED).message(ImmutableMap.<String, Object>of("error", duplicacyException.errorCode.name(), "reason", duplicacyException.getMessage())).build();
        } catch (Exception ex){
            return PlanResponse.builder().id(null).uniquePlanId(plan.getUniquePlanId()).status(PlanStatus.CREATION_FAILED).message(ImmutableMap.<String, Object>of("error", "UNKNOWN_ERROR", "reason", ex.getMessage())).build();
        }
    }

}
