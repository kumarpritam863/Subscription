package com.kumarpritam.mappers;

import com.kumarpritam.entitites.PlanEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanMapper implements RowMapper<PlanEntity> {
    @Override
    public PlanEntity mapRow(ResultSet resultSet, int i) throws SQLException {
        return PlanEntity.builder().planId(resultSet.getLong("plan_id")).uniqueId(resultSet.getString("unique_id")).period(resultSet.getString("period")).interval(resultSet.getString("period_interval")).product(resultSet.getString("product")).createdAt(resultSet.getTimestamp("created_at")).updatedAt(resultSet.getTimestamp("updated_at")).build();
    }
}
