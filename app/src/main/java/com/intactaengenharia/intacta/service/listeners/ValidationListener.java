package com.intactaengenharia.intacta.service.listeners;

public class ValidationListener {

    private Boolean mStatus = true;
    private String mValidationMessage = "";

    public ValidationListener(String errorMessage) {
        if (!errorMessage.equals("")) {
            mStatus = false;
            mValidationMessage = errorMessage;
        }
    }

    public ValidationListener() { }

    public Boolean success() { return mStatus; }

    public String failure() { return mValidationMessage; }

}

