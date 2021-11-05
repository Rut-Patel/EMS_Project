/*
 *UserDA - this file is contains all of the data access methods, that actually get/set data to the database.
 * @author Rut Patel
 * @version 3.0 (17 March 2021)
 * @since 1.0
 */
package com.project;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Date;

import static com.project.User.Digest;

public class UserDA {
    /**
     * Declaring aUser.
     */
    static User aUser;

    /**
     * Declaring Connection variable of type Connection
     */
    static Connection aConnection;

    /**
     * Declaring a variable with type statement for the use of prepared statement.
     */
    static Statement aStatement;

    /**
     * Declaring Id field to store ID of the user.
     */
    static long id;
    /**
     * Declaring password of type string
     */
    static String password;
    /**
     * Declaring firstname of type string
     */
    static String firstName;
    /**
     * Declaring lastname of type string
     */
    static String lastName;
    /**
     * Declaring email address of type string
     */
    static String emailAddress;
    /**
     * Declaring lastAccess of type Date
     */
    static Date lastAccess;
    /**
     * Declaring enrol date of type Date
     */
    static Date enrolDate;
    /**
     * Declaring enabled of type boolean.
     */
    static boolean enabled;
    /**
     * Declaring type of char.
     */
    static char type;

    //private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Establishing the connection.
     * @param c;
     */
    public static void initialize(Connection c)
    {
        try {
            aConnection=c;
            aStatement=aConnection.createStatement();
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    /**
     * close the database connection
     */
    public static void terminate()
    {
        try
        { 	// close the statement
            aStatement.close();
        }
        catch (SQLException e)
        { System.out.println(e);	}
    }

    /**
     *This method retrieves the user Information from the database
     * @param id;
     * @return User
     * @throws NotFoundException: The is thrown when their is not data with the input ID.
     */
    public static User retrieve(long id) throws NotFoundException {
        aUser = null;

        try {
            PreparedStatement psRetrieve = aConnection.prepareStatement("SELECT Id, Password, FirstName, LastName, EmailAddress, LastAccess,EnrolDate, Enable, Type FROM users WHERE Id = ? ;");
            psRetrieve.setLong(1,id);
            psRetrieve.execute();
            ResultSet rs = aStatement.executeQuery(String.valueOf(psRetrieve));

            boolean gotData = rs.next();
            if (gotData) {
                password = rs.getString("Password");
                firstName = rs.getString("FirstName");
                lastName = rs.getString("LastName");
                emailAddress = rs.getString("EmailAddress");
                lastAccess = rs.getTimestamp("LastAccess");
                enrolDate = rs.getTimestamp("EnrolDate");
                enabled = rs.getBoolean("Enable");
                type = rs.getString("Type").charAt(0);

                try {
                    aUser = new User(id, password, firstName, lastName, emailAddress, enrolDate, lastAccess, enabled, type);
                } catch (InvalidUserDataException e) {
                    System.out.println("Your record Contains some invalid Data.");
                }
            } else {
                throw (new NotFoundException("Problem retrieving user record, with id: " + id + " does not exist in the system."));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return aUser;
    }


    /**
     * Creating the user by inserting a record into the database.
     * @param aUser;
     * @return boolean
     * @throws DuplicateException: This exception is thrown when their is already a user with the same ID.
     */
    public static boolean create(User aUser) throws DuplicateException
    {
        boolean inserted = false;
        id = aUser.getId();
        password = aUser.getPassword();
        firstName = aUser.getFirstName();
        lastName = aUser.getLastName();
        emailAddress = aUser.getEmailAddress();
        lastAccess = aUser.getLastAccess();
        enrolDate = aUser.getEnrolDate();
        enabled = true;
        type = aUser.getType();


        try
        {
            retrieve(id);
            throw(new DuplicateException("Problem with creating User, id: " + id +" already exists in the system."));
        }
        catch(NotFoundException e)
        {
            try
            {  // execute the SQL update statement
                PreparedStatement psInsertUsers = aConnection.prepareStatement("INSERT INTO users (Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enable,Type) VALUES (?,?,?,?,?,?,?,?,?)");
                psInsertUsers.setLong(1,id);
                psInsertUsers.setString(2,Digest(password,"sha1"));
                psInsertUsers.setString(3,firstName);
                psInsertUsers.setString(4,lastName);
                psInsertUsers.setString(5,emailAddress);
                psInsertUsers.setDate(6, new java.sql.Date(lastAccess.getTime()));
                psInsertUsers.setDate(7, new java.sql.Date(enrolDate.getTime()));
                psInsertUsers.setBoolean(8, enabled);
                psInsertUsers.setString(9, String.valueOf(type));

                inserted = (psInsertUsers.executeUpdate() == 1);
            }
            catch (SQLException | NoSuchAlgorithmException ee)
            {
                System.out.println(ee);
            }
        }
        return inserted;
    }

    /**
     * This method updates the user information.
     * @param aUser;
     * @return int
     * @throws NotFoundException: This is thrown when the users is not found in the system.
     */
    public static int update(User aUser) throws NotFoundException
    {
        int records = 0;

        id = aUser.getId();
        password = aUser.getPassword();
        firstName = aUser.getFirstName();
        lastName = aUser.getLastName();
        emailAddress = aUser.getEmailAddress();
        lastAccess = aUser.getLastAccess();
        enrolDate = aUser.getEnrolDate();
        enabled = true;
        type = aUser.getType();

        // NotFoundException is thrown by find method
        try
        {
            User.retrieve(id);  //determine if there is a Customer record to be updated
            // if found, execute the SQL update statement

            PreparedStatement psInsertUsers = aConnection.prepareStatement("UPDATE users SET Password = ? ,FirstName = ?,LastName = ?,EmailAddress = ? ,LastAccess = ? ,EnrolDate = ?, Enable = ? ,Type = ? WHERE Id = ?");
            psInsertUsers.setLong(9,id);
            psInsertUsers.setString(1,Digest(password,"sha1"));
            psInsertUsers.setString(2,firstName);
            psInsertUsers.setString(3,lastName);
            psInsertUsers.setString(4,emailAddress);
            psInsertUsers.setDate(5, new java.sql.Date(lastAccess.getTime()));
            psInsertUsers.setDate(6, new java.sql.Date(enrolDate.getTime()));
            psInsertUsers.setBoolean(7, enabled);
            psInsertUsers.setString(8, String.valueOf(type));

        }catch(NotFoundException e)
        {
            throw new NotFoundException("User with id " + id  + " cannot be updated, does not exist in the system.");
        }catch (SQLException e)
        { System.out.println(e);} catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * This method deletes the record from the system.
     * @param aUser;
     * @return int;
     * @throws NotFoundException:This exception is thrown when no user with that ID is found.
     */
    public static int delete(User aUser) throws NotFoundException
    {
        int records = 0;
        // retrieve the id (key)
        id = aUser.getId();
        // create the SQL delete statement
        String sqlDeleteUsers = "DELETE FROM users " +
                "WHERE Id = '" + id +"'";

        String sqlDeleteStudents = "DELETE FROM students " +
                "WHERE StudentId = '" + id +"'";
        // see if this customer already exists in the database
        try
        {
            User.retrieve(id);  //used to determine if record exists for the passed Customer
            // if found, execute the SQL update statement
            records = aStatement.executeUpdate(sqlDeleteStudents);
            records = aStatement.executeUpdate(sqlDeleteUsers);

        }catch(NotFoundException e)
        {
            throw new NotFoundException("User with id " + id
                    + " cannot be deleted, does not exist.");
        }catch (SQLException e)
        { System.out.println(e);	}
        return records;
    }

}
