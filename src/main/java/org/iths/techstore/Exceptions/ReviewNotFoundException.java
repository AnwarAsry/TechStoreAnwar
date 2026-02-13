package org.iths.techstore.Exceptions;

public class ReviewNotFoundException extends RuntimeException {
    // Constructor
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
