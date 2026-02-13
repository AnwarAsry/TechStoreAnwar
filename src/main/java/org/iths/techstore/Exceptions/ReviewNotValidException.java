package org.iths.techstore.Exceptions;

public class ReviewNotValidException extends RuntimeException {
    // Constructor
    public ReviewNotValidException(String message) {
        super(message);
    }
}
