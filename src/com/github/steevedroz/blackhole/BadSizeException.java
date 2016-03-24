package com.github.steevedroz.blackhole;

public class BadSizeException extends Exception {
    private static final long serialVersionUID = 1L;

    public BadSizeException(String message) {
	super(message);
    }
}
