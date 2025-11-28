package org.example.POJOS;

public class Exceptions extends Exception {
    private ValidationError error;

    public Exceptions(ValidationError error) {
        super("Validation error" + error.name());
        this.error = error;
    }

    public ValidationError getError() {
        return error;
    }
}