package com.kumarpritam.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import com.kumarpritam.dao.PlanDao;
import com.kumarpritam.entitites.PlanEntity;
import com.kumarpritam.enums.PlanStatus;
import com.kumarpritam.exceptions.DuplicacyException;
import com.kumarpritam.exceptions.PlanValidationException;
import com.kumarpritam.models.Plan;
import com.kumarpritam.responses.PlanResponse;
import com.kumarpritam.validators.PlanValidator;
import io.micrometer.core.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PlanService {

    private Logger log = LoggerFactory.getLogger(PlanService.class.getName());

    @Autowired private PlanValidator planValidator;
    @Autowired private PlanDao planDao;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TransactionTemplate transactionTemplate;

    @Transactional(rollbackFor = Exception.class)
    public PlanResponse createPlan(@NonNull Plan plan) throws PlanValidationException, DuplicacyException, JsonProcessingException {
        planValidator.validatePlan(plan);
        log.info("Saving plan");
        PlanEntity planEntity;
        planDao.savePlan(plan);
        planEntity = planDao.retrievePlan(plan.getUniquePlanId());
        try{
            planValidator.throwException();
        } catch (Exception ex){
            log.error("exception caught");
        }
        return PlanResponse.builder().id(Long.toString(planEntity.getPlanId())).uniquePlanId(plan.getUniquePlanId()).status(PlanStatus.CREATED).message(ImmutableMap.<String, Object>of("error", "null")).build();
    }
}
