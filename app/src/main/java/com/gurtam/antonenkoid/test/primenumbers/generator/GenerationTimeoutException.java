package com.gurtam.antonenkoid.test.primenumbers.generator;

/**
 * Must be thrown when generation timeout achieved.
 *
 * @author antonenkoid
 */
public class GenerationTimeoutException extends Exception {

    GenerationTimeoutException(String message) {
        super(message);
    }

    public GenerationTimeoutException() {
        super();
    }

    public GenerationTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenerationTimeoutException(Throwable cause) {
        super(cause);
    }
}
