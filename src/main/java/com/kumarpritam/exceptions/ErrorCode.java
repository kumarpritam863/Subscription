package com.kumarpritam.exceptions;


public enum ErrorCode {

    INVALID_PLAN("invalid Plan", ""),
    DUPLICATE_UNIQUE_PLAN_ID("plan with unique plan id already exist", ""),
    PLAN_NOT_FOUND("No plan found by planId", ""),
    ACTIVE_SUBSCRIPTION_FOUND("Subscription is already active", "");

    public String displayMessage;
    public String errorMessage;

    ErrorCode(String errorMessage, String displayMessage){
        this.errorMessage = errorMessage;
        this.displayMessage = displayMessage;
    }
}
