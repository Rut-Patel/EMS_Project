package com.project;

/***
 Creating the exception class for the Newfound Exception.
 * @author  Rut Patel
 * @version 1 (Jan 29 2021)
 */
public class NotFoundException extends Exception{

    /***
     *Creating parametrized constructor for Not found Exception.
     * @param s;
     */
    public NotFoundException(String s) {
        super(s);
    }

    /**
     * Creating a parametrized constructor.
     */
    public NotFoundException(){
        super();
    }
}
