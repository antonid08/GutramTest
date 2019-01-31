package com.gurtam.antonenkoid.test.primenumbers.generation;

import java.util.Date;

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

    private boolean isStarted = false;

    public void start() {
        startTime = System.currentTimeMillis();
        isStarted = true;
    }

    public long finish() {
        if (!isStarted) {
            throw new IllegalStateException("Start must be called before call finish");
        }

        isStarted = false;
        return System.currentTimeMillis() - startTime;
    }

    public Date getStartTime() {
        return new Date(startTime);
    }

    public boolean isStarted() {
        return isStarted;
    }

}
