package com.kumarpritam.validators;

import com.kumarpritam.dao.PlanDao;
import com.kumarpritam.entitites.PlanEntity;
import com.kumarpritam.enums.PlanStatus;
import com.kumarpritam.exceptions.DuplicacyException;
import com.kumarpritam.exceptions.ErrorCode;
import com.kumarpritam.exceptions.PlanValidationException;
import com.kumarpritam.models.Plan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class PlanValidator {

    private Logger log = LoggerFactory.getLogger(PlanValidator.class.getName());

    @Autowired private PlanDao planDao;

    public void validatePlan(Plan plan) throws PlanValidationException, DuplicacyException {
        if(plan.getPeriod().notIn())
            throw new PlanValidationException(ErrorCode.INVALID_PLAN, "Invalid plan period");
        switch(plan.getPeriod()){
            case DAILY:
                if(plan.getInterval() < 7)
                    throw new PlanValidationException(ErrorCode.INVALID_PLAN, "For daily period type, minimum supported interval is 7");
            case WEEKLY:
            case MONTHLY:
            case YEARLY:
                if(plan.getInterval() < 1)
                    throw new PlanValidationException(ErrorCode.INVALID_PLAN, "For weekly, monthly or yearly period type, minimum supported interval is 1");
        }
        if(plan.getProduct().getId() <= 0)
            throw new PlanValidationException(ErrorCode.INVALID_PLAN, "Id should be grater than 0");
        if(StringUtils.isEmpty(plan.getProduct().getName()))
            throw new PlanValidationException(ErrorCode.INVALID_PLAN, "Name of the product cannot be empty");
        if(planDao.isPlanExist(plan.getUniquePlanId()))
            throw new DuplicacyException(ErrorCode.DUPLICATE_UNIQUE_PLAN_ID, ErrorCode.DUPLICATE_UNIQUE_PLAN_ID.errorMessage);
    }

    @Transactional
    public void throwException() throws Exception {
        throw new Exception();
    }
}
