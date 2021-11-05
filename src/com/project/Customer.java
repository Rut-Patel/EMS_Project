/*
 *Student class which is extending the users class.
 * @author Rut Patel
 * @version 3.0 (02 January 2021)
 * @since 1.0
 */
package com.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

/**
 * @author : Rut Patel
 * @version : 1 (17 Jan 2021)
 */
public class Customer extends User{

    /**
     * Initialize the connection.
     * @param c;
     */
    public static void initialize(Connection c)
    {
        CustomerDA.initialize(c);
    }

    /**
     * Retrieves the data for the student class.
     * @param id;
     * @return student
     * @throws NotFoundException if the data record is not found in the database.
     */
    public static Customer retrieve(long id) throws NotFoundException
    {
        return CustomerDA.retrieve(id);
    }

    /**
     *Terminates the connection.
     */
    public static void terminate()
    {
        CustomerDA.terminate();
    }

    /**
     *Creates the record.
     * @return boolean
     * @throws DuplicateException is thrown if the data is getting duplicated.
     */
    public boolean create() throws DuplicateException, SQLException {
        return CustomerDA.create(this);
    }

    /**
     * Using the ID and password to authenticate the user.
     * @param id;
     * @param password;
     * @return Student;
     * @throws NotFoundException;
     */
    public static Customer login(long id,String password) throws NotFoundException
    {
        return CustomerDA.authenticate(id,password);
    }

    /**
     *Check the users information using ID.
     * @param id;
     * @return exits or not
     * @throws SQLException;
     */
    public static boolean isExistingLogin(long id) throws SQLException {
        return CustomerDA.isExistingLogin(id);
    }

    /**
     * Deletes the record.
     * @return Integer
     * @throws NotFoundException if the data record is not found in the database.
     */
    public Integer delete() throws NotFoundException
    {
        return CustomerDA.delete(this);
    }

    /**
     *Updates the student records.
     * @return integer
     * @throws NotFoundException if the data record is not found in the database.
     */
    public Integer update() throws NotFoundException
    {
        return CustomerDA.update(this);
    }



    public static String DEFAULT_UNIT = "00A" ;

    public static String DEFAULT_ADDRESS = "Street Name" ;

    public static String DEFAULT_CITY = "CITY" ;

    public static String DEFAULT_POSTALCODE = "A1B2C3" ;


    /**
     * Creating instance variable for program code.
     */
    private String unit ;

    /**
     * Creating instance variable for program Description.
     */
    private String address ;

    /**
     * Creating instance variable for year.
     */
    private String postalcode ;
    /**
     * Creating instance variable for year.
     */
    private String city ;


    //Getters for the class student.

    /**
     * Getter for the Program code.
     * @return programCode
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Getter for the program Description
     * @return programDescription
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the year.
     * @return year
     */
    public String getPostalCode() {
        return postalcode;
    }

    /**
     * Getter for the vector of mark.
     * @return marks
     */
    public String getCity() {
        return city;
    }

    //Setters for the student class.

    /**
     * Setting value of the program code.
     * @param unit;
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Setting program Description for Student Class.
     * @param address;
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Setting the year.
     * @param postalcode;
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Creating the parametrized constructor without the marks vector.
     * @param id ;
     * @param password ;
     * @param firstName ;
     * @param lastName ;
     * @param emailAddress ;
     * @param lastAccess ;
     * @param enrolDate ;
     * @param enabled ;
     * @param type ;
     * @param unit ;
     * @param address ;
     * @param postalcode ;
     * @param city ;
     * @throws InvalidUserDataException;
     */
    public Customer(long id, String password, String firstName, String lastName, String emailAddress, Date lastAccess, Date enrolDate, boolean enabled, char type, String unit, String address, String postalcode, String city) throws InvalidUserDataException {
        super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);
        setUnit(unit);
        setPostalcode(postalcode);
        setAddress(address);
        setCity(city);
    }


    /**
     * Default constructor setting all the values to the default class value.
     * @throws InvalidUserDataException;
     */
    public Customer() throws InvalidUserDataException {
        this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME,
                DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS,
                new Date(), new Date(),DEFAULT_ENABLED_STATUS ,
                DEFAULT_TYPE, DEFAULT_UNIT, DEFAULT_ADDRESS, DEFAULT_CITY, DEFAULT_POSTALCODE);

    }

    /**
     * Setting the type to the student for the display.
     * @return String;
     */
    public String getTypeForDisplay() { return "Customer"; }

    /**
     * Creating to string method to display the message.
     * @return String;
     */
    @Override
    public String toString() {
        return  getTypeForDisplay() + " Info for:" +
                "\n" + getFirstName() + " " + getLastName() + " (" + getId() + ")" +
                "\nAddress: - "+ getUnit() +" " + getAddress()+ ", " + getCity() + "." +
                "\nPostal Code: - "+ getPostalCode();

    }

}
