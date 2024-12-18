package org.concurrency.exception;

public class EnrichmentException extends Exception {
    public EnrichmentException(String message) {
        super(message);
    }

    public EnrichmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
