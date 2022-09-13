package com.daniloalalmeida.estudosjava.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3775541251240667424L;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }

    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
