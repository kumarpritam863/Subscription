package com.kumarpritam.exceptions;

public class DuplicacyException extends Exception {
    public ErrorCode errorCode;

    public DuplicacyException(ErrorCode errorCode){
        super(errorCode.errorMessage);
        this.errorCode = errorCode;
    }

    public DuplicacyException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
