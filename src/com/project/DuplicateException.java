package com.project;

/**
 * Creating the exception class for the Duplicate Record Exception.
 * @author  Rut Patel
 * @version 1 (Jan 29 2021)
 */
public class DuplicateException extends Exception{

    /**
     * Creating a parametrized constructor for the class.
     * @param s;
     */
    public DuplicateException(String s) {
        super(s);
    }

    /**
     * Creating a default Constructor.
     */
    public DuplicateException(){
        super();
    }
}
