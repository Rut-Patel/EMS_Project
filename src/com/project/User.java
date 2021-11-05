package com.project;


import java.security.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author : Rut Patel
 * @version : 1 (17 Jan 2021)
 */
public class User {

    /**
     * Calling the initialize from the UserDA class
     * @param c;
     */
    public static void initialize(Connection c)
    {
        UserDA.initialize(c);
    }

    /**
     * Calling retrieve from the UserDA class.
     * @param id;
     * @return user
     * @throws NotFoundException: the data is not found.
     */
    public static User retrieve(long id) throws NotFoundException
    {
        return UserDA.retrieve(id);
    }

    /**
     * Caling the terminate method from UserDA
     */
    public static void terminate()
    {
        UserDA.terminate();
    }

    /**
     * Calling the create method from UserDA class,
     * @return boolean;
     * @throws DuplicateException:if the record already exists in the system.
     * @throws SQLException:And databse related errors.
     */
    public boolean create() throws DuplicateException, SQLException {
        return UserDA.create(this);
    }

    /**
     * Calling the delete method from UserDa Class.
     * @return Integer;
     * @throws NotFoundException:if not record is found.
     */
    public Integer delete() throws NotFoundException
    {
        return UserDA.delete(this);
    }

    /**
     * Calling the update method from the UserDa class.
     * @return integer.
     * @throws NotFoundException:If data is not found.
     */
    public Integer update() throws NotFoundException
    {
        return UserDA.update(this);
    }



    /**
     * Declaring shared class constant for the ID.
     */
    public static final long DEFAULT_ID = 100123456L ;
    /**
     * Declaring shared class constant for default password.
     */
    public static final String DEFAULT_PASSWORD  = "password" ;

    /**
     * Declaring shared class constant for Minimum length of the password.
     */
    public static final byte MINIMUM_PASSWORD_LENGTH  = 8 ;
    /**
     * Declaring shared class constant for maximum length of the password.
     */
    public static final byte MAXIMUM_PASSWORD_LENGTH  = 40 ;
    /**
     * Declaring shared class constant for the default first name with the value as John.
     */
    public static final String DEFAULT_FIRST_NAME = "John" ;
    /**
     * Declaring shared class constant for the default last name with the value as Doe.
     */
    public static final String DEFAULT_LAST_NAME = "Doe" ;
    /**
     * Declaring shared class constant for the email address with the default value as john.doe@dcmail.com.
     */
    public static final String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com" ;
    /**
     * Declaring shared class constant for the enabled status of user..
     */
    public static final boolean DEFAULT_ENABLED_STATUS  = true ;
    /**
     * Declaring shared class constant for the default type of user .
     */
    public static final char DEFAULT_TYPE  = 's' ;

    /**s
     * Declaring shared class constant for the date.
     */
    public static final  DateFormat DF = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA);


    /**
     * Declaring the instance attribute id for storing user's unique identification number.
     */
    private long id;

    /**
     * Instance attribute to store user's password.
     */
    private String password;

    /**
     * Instance attribute to store user's First Name.
     */
    private String firstName;

    /**
     * Instance attribute to store user's last Name.
     */
    private String lastName;

    /**
     * Instance attribute to store user's Email Address.
     */
    private String emailAddress;

    /**
     * Instance attribute to store the last time when user accessed the system.
     */
    private Date lastAccess;

    /**
     * Instance attribute to store when user was created in the system.
     */
    private Date enrolDate;

    /**
     * Instance attribute to store whether the user is enabled to access the system.
     */
    private boolean enabled;

    /**
     * Instance attribute to store user's type as a single character.
     */
    private char type;



    //Setters for the user class

    /**
     * Sets the value of ID.
     * @param id;
     */
    public void setId(long id) throws InvalidIdException {
        if(id > 0){
            this.id = id ;
        }else {
            throw new InvalidIdException("ID must be Numbers");
        }
    }

    /**
     * Sets the password for the user.
     * @param password;
     */
    public void setPassword(String password) throws InvalidPasswordException {
        if (password.length() >= MINIMUM_PASSWORD_LENGTH && password.length() <= MAXIMUM_PASSWORD_LENGTH)
        {
            this.password = password;
        }else
        {
            throw new InvalidPasswordException("Please enter the password having length 3 to 15");
        }
    }

    /**
     * Sets the firstname of the user.
     * @param firstName;
     */
    public void setFirstName(String firstName) throws InvalidNameException {
        if(firstName.isEmpty())
        {
            throw new InvalidNameException ("Please enter your First Name.");
        }//else if (firstName.matches(".*\\d.*"))
//        {
//            throw new InvalidNameException ("Your Name should not contain number in it.");
//        }
        else
        {
            this.firstName = firstName;
        }

    }

    /**
     * Sets the lastname of the user.
     * @param lastName;
     */
    public void setLastName(String lastName) throws InvalidNameException {
        if(lastName.isEmpty())
        {
            throw new InvalidNameException ("Please enter your First Name.");
        }//else if (lastName.matches(".*\\d.*"))
//        {
//            throw new InvalidNameException ("Your Name should not contain number in it.");
//        }
        else
        {
            this.lastName = lastName;
        }

    }

    /**
     * Sets the email Address of the user.
     * @param emailAddress;
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the time when the user access the system last time.
     * @param lastAccess;
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * Sets the date when the user was created.
     * @param enrolDate;
     */
    public void setEnrolDate(Date enrolDate) {
        this.enrolDate = enrolDate;
    }

    /**
     * Sets the enable status of the user.
     * @param enabled;
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the type of users.
     * @param type;
     */
    public void setType(char type) {
        this.type = type;
    }

    //Getters for the user class.

    /**
     * Gets the user's ID.
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the user Password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user's firstname.
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the user's lastname.
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the user's Email-Address.
     * @return emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets the user's last Access time.
     * @return lastAccess
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * Gets the user's Enroll date or when the user was created.
     * @return enrolDate
     */
    public Date getEnrolDate() {
        return enrolDate;
    }

    /**
     * Gets the enable status 0f the user.
     * @return enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Gets the type of user.
     * @return type
     */
    public char getType() {
        return type;
    }


    /**
     * Parametrized Constructor.
     * @param id;
     * @param password;
     * @param firstName;
     * @param lastName;
     * @param emailAddress;
     * @param lastAccess;
     * @param enrolDate;
     * @param enabled;
     * @param type;
     * @throws NotFoundException;
     */
    public User(long id, String password, String firstName, String lastName, String emailAddress,  Date enrolDate,Date lastAccess, boolean enabled, char type) throws InvalidUserDataException {

        try {
            setId(id);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
        }catch(Exception e)
        {
            throw new InvalidUserDataException(e.getMessage());
        }
        setEmailAddress(emailAddress);
        setLastAccess(lastAccess);
        setEnrolDate(enrolDate);
        setEnabled(enabled);
        setType(type);
    }

    public User() throws InvalidUserDataException {

        this(DEFAULT_ID,DEFAULT_PASSWORD,DEFAULT_FIRST_NAME,DEFAULT_LAST_NAME,DEFAULT_EMAIL_ADDRESS,new Date(),new Date(),DEFAULT_ENABLED_STATUS,DEFAULT_TYPE);

    }
    /**
     * Declaring the method to display the type of user.
     */
    public String getTypeForDisplay()
    {
        return "User";
    }


    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * ToString() for displaying message.
     * @return String
     *
     */
    @Override
    public String toString() {
        return  "User Info for: " + id +
                "\nName: '" + firstName + " " +lastName +" (" + emailAddress + ")" +
                "\nCreated On: " + formatter.format(enrolDate) +
                "\nLast Access: " + formatter.format(lastAccess) ;
    }

    /**
     * Displaying the toString method.
     */
    public void dump() {
        System.out.println(toString());
    }

    public static String Digest(String password,String type) throws NoSuchAlgorithmException
    {
        String hex = "";
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            md.update(password.getBytes());
            byte[] bytesOfHashedString = md.digest();


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytesOfHashedString.length; i++) {
                sb.append(String.format("%02x", bytesOfHashedString[i]));
            }
            hex = sb.toString();

        }catch (NoSuchAlgorithmException e)
        {
            System.out.print(e);
        }
        return hex;
     }

}
