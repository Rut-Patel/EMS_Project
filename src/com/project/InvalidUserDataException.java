package com.project;


/**
 * Creating the exception class for the InvalidUserDataException.
 * @author  Rut Patel
 * @version 1 (Jan 17 2021)
 */
public class InvalidUserDataException extends Exception {

    /**
     * Constructor for the Exception.
     * @param s;
     */
    public InvalidUserDataException(String s) {
        super(s);
    }

    public InvalidUserDataException(){
        super();
    }

}
