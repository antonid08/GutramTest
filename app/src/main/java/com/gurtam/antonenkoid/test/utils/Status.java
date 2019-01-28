package com.gurtam.antonenkoid.test.utils;

public class Status {

    private final boolean isInProgress;

    private final String error;

    private Status(boolean isInProgress, String error) {
        this.isInProgress = isInProgress;
        this.error = error;
    }

    public static Status onStart() {
        return new Status(true, null);
    }

    public static Status onSuccess() {
        return new Status(false, null);
    }

    public static Status onError(String errorText) {
        return new Status(false, errorText);
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public boolean isSuccess() {
        return getError() == null;
    }

    public String getError() {
        return error;
    }


}
