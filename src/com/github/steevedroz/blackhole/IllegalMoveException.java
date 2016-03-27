package com.github.steevedroz.blackhole;

public class IllegalMoveException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalMoveException(String message) {
	super(message);
    }
}
