package com.kumarpritam.queries;

public class PlanQuery {
    public static final String FETCH_PLAN_BY_UNIQUE_PLAN_ID = "SELECT * FROM Plan WHERE unique_id = ?";
    public static final String SAVE_PLAN = "INSERT INTO Plan (unique_id, period, period_interval, product, status) VALUES (?, ?, ?, ?, ?)";
    public static final String FETCH_PLAN_BY_PLAN_ID = "SELECT * FROM Plan WHERE plan_id = ?";
    public static final String UPDATE_PLAN_STATUS = "UPDATE Plan SET status = ? where plan_id = ?";
}
