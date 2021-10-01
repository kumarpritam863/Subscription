package com.kumarpritam.enums;

public enum Period {
    DAILY, WEEKLY, MONTHLY, YEARLY;

    public Boolean notIn(){
        if(this != DAILY && this != WEEKLY && this != MONTHLY && this != YEARLY)
            return true;
        return false;
    }
}
