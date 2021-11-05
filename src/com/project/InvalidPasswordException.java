package com.project;


/**
 * Creating the exception for the Invalid password Exception
 * @author Rut Patel
 * @version 1 (17 Jan 2021)
 */
public class InvalidPasswordException extends Exception {
    /**
     * Creating the constructor for the exception.
     * @param s;
     */
    public InvalidPasswordException(String s) {
        super(s);
    }

    public InvalidPasswordException(){
        super();
    }
}
