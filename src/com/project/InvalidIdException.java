package com.project;

/**
 * Creating the exception class for the InvalidIDException.
 * @author  Rut Patel
 * @version 1 (Jan 17 2021)
 */
public class InvalidIdException extends Exception {

    /**
     * Creating the constructor for the exception.
     * @param s;
     */
    public InvalidIdException(String s) {
        super(s);
    }

    /**
     * Creating default Constructor.
     */
    public InvalidIdException(){
        super();
    }
}
