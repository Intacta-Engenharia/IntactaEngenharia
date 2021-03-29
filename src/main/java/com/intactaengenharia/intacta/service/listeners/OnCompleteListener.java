package com.intactaengenharia.intacta.service.listeners;

public interface OnCompleteListener<T> {
    void onSuccess(T result);
    void onFailure(String message);
}
