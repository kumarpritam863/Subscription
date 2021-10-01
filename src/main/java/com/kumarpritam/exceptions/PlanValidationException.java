package com.kumarpritam.exceptions;

public class PlanValidationException extends Exception {
    public ErrorCode errorCode;

    public PlanValidationException(ErrorCode errorCode){
        super(errorCode.errorMessage);
        this.errorCode = errorCode;
    }

    public PlanValidationException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
