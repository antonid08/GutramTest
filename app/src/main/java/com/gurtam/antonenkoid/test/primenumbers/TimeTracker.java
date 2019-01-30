package com.gurtam.antonenkoid.test.primenumbers;

/**
 * Simple time tracker.
 *
 * <p>Usage: call {@link #start()} to start tracking time, call {@link #finish()} to finish tracking.</p>
 *
 * WARNING: {@link #finish()} must not be called before {@link #start()}
 *
 * @author antonenkoid
 */
public class TimeTracker {

    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long finish() {
        if (startTime == 0) {
            throw new IllegalStateException("Start must be called before call finish");
        }

        long passedTime = System.currentTimeMillis() - startTime;
        startTime = 0;

        return passedTime;
    }

    public boolean hasStarted() {
        return startTime != 0;
    }

}
