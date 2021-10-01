package com.kumarpritam.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumarpritam.entitites.PlanEntity;
import com.kumarpritam.enums.PlanStatus;
import com.kumarpritam.mappers.PlanMapper;
import com.kumarpritam.models.Plan;
import com.kumarpritam.queries.PlanQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class PlanDao {

    private Logger log = LoggerFactory.getLogger(PlanDao.class.getName());

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private ObjectMapper objectMapper;

    public boolean isPlanExist(String uniquePlanId){
        List<PlanEntity> planEntityList;
        try{
            planEntityList = jdbcTemplate.query(PlanQuery.FETCH_PLAN_BY_UNIQUE_PLAN_ID, new Object[]{uniquePlanId}, new PlanMapper());
            if(Objects.nonNull(planEntityList) && !planEntityList.isEmpty())
                return true;
            return false;
        } catch (Exception exception){
            log.error("Exception occurred while checking for existence of the plan for unique plan id = {}. Error is :: {}", uniquePlanId, exception.getMessage());
            throw exception;
        }
    }

    public void savePlan(Plan plan) throws JsonProcessingException {
        try{
            jdbcTemplate.update(PlanQuery.SAVE_PLAN, new Object[]{plan.getUniquePlanId(), plan.getPeriod().name(), plan.getInterval(), objectMapper.writeValueAsString(plan.getProduct()), PlanStatus.ACTIVE.name()});
        } catch (Exception ex){
            log.error("An exception occurred while saving plan for unique_plan_id = {}. Error is {}", plan.getUniquePlanId(), ex.getMessage());
            throw ex;
        }
    }

    public PlanEntity retrievePlan(String uniquePlanId){
        try{
            List<PlanEntity> planEntityList = jdbcTemplate.query(PlanQuery.FETCH_PLAN_BY_UNIQUE_PLAN_ID, new Object[]{uniquePlanId}, new PlanMapper());
            if(Objects.nonNull(planEntityList) && planEntityList.size() == 1)
                return planEntityList.get(0);
        } catch (Exception ex){
            log.error("An exception occurred while retrieving plan for unique plan id = {}. Error is {}", uniquePlanId, ex.getMessage());
        }
        return null;
    }

    public PlanEntity retrievePlanByPlanId(String uniquePlanId){
        try{
            List<PlanEntity> planEntityList = jdbcTemplate.query(PlanQuery.FETCH_PLAN_BY_PLAN_ID, new Object[]{uniquePlanId}, new PlanMapper());
            if(Objects.nonNull(planEntityList) && planEntityList.size() == 1)
                return planEntityList.get(0);
        } catch (Exception ex){
            log.error("An exception occurred while retrieving plan for unique plan id = {}. Error is {}", uniquePlanId, ex.getMessage());
        }
        return null;
    }
}
