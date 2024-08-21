package com.lucifiere.epf;

public class EpfException extends RuntimeException {

    public EpfException() {
    }

    public EpfException(String message) {
        super(message);
    }

    public EpfException(String message, Throwable cause) {
        super(message, cause);
    }

    public EpfException(Throwable cause) {
        super(cause);
    }

    public EpfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
