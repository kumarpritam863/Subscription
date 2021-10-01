package com.kumarpritam.exceptions;

public class SubscriptionValidationException extends Exception {
    public ErrorCode errorCode;

    public SubscriptionValidationException(ErrorCode errorCode){
        super(errorCode.errorMessage);
        this.errorCode = errorCode;
    }

    public SubscriptionValidationException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
