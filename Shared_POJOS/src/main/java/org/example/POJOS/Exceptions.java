package org.example.POJOS;

public class Exceptions extends Exception {
    private Error error;

    public Exceptions(Error error) {
        this.error = error;
    }
}