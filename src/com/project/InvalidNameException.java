package com.project;

/**
 * Creating the exception class for the InvalidNameException.
 * @author  Rut Patel
 * @version 1 (Jan 17 2021)
 */
public class InvalidNameException extends Exception {
    /**
     * Creating the constructor for the exception.
     * @param s;
     */
    public InvalidNameException(String s) {
        super(s);
    }

    public InvalidNameException() {
        super();
    }
}
