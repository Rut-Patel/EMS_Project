/*
 *CustomerDA - this file is contains all of the data access methods, that actually get/set data to the database.
 * @author Rut Patel
 * @version 3.0 (02 January 2021)
 * @since 1.0
 */
package com.project;


import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.project.User.Digest;


public class CustomerDA {

    /**
     * declaring aCustomer of type Student.
     */
    static Customer aCustomer;
    /**
     * Declaring the connection of type Connection.
     */
    static Connection aConnection;
    /**
     * Declaring the statement of type Statement.
     */
    static Statement aStatement;
    /**
     * Declaring programCode of type string.
     */
    static String unit ;
    /**
     * Declaring programDescription of type string.
     */
    static String address ;
    /**
     * Declaring address of type String.
     */
    static String postalCode;
    /**
     *Declaring year of type int.
     */
    static String city ;
    /**
     * Declaring id of type long.
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
     * Declaring emailaddress of type string
     */
    static String emailAddress;
    /**
     * Declaring lastAccess of type Date
     */
    static Date lastAccess;
    /**
     * Declaring enroldate of type Date
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
     * Retrieves the data by querying.
     * @param id;
     * @return Student
     * @throws NotFoundException if the data record is not found in the database.
     */
    public static Customer retrieve(long id) throws NotFoundException {
        aCustomer = null;

        try {
            PreparedStatement psRetrieve = aConnection.prepareStatement("SELECT users.Id, Password, FirstName, LastName, EmailAddress, LastAccess,EnrolDate, Enable, Type,unit,address,city,postalcode FROM users, Customer WHERE users.id = Customer.cusid AND users.Id = ? ;");
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
                unit = rs.getString("unit");
                address = rs.getString("address");
                city = rs.getString("city");
                postalCode = rs.getString("postalcode");
                enabled = rs.getBoolean("Enable");
                type = rs.getString("Type").charAt(0);

                try {
                    aCustomer = new Customer(id, password, firstName, lastName, emailAddress, enrolDate, lastAccess, enabled, type, unit, address, city,postalCode);
                } catch (InvalidUserDataException e) {
                    System.out.println("Your record Contains some invalid Data.");
                }
            } else {
                throw (new NotFoundException("Problem retrieving Student record, with id: " + id + " does not exist in the system."));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return aCustomer;
    }

    /**
     * Creates a record in the database.
     * @param aCustomer;
     * @return boolean
     * @throws DuplicateException is thrown if the data is getting duplicated.
     */
    public static boolean create(Customer aCustomer) throws DuplicateException, SQLException {
        boolean inserted = false;
        try {
            aConnection.setAutoCommit(false);
            id = aCustomer.getId();
            unit = aCustomer.getUnit();
            address = aCustomer.getAddress();
            city = aCustomer.getCity();
            postalCode = aCustomer.getPostalCode();


            if (UserDA.create(aCustomer)) {
                PreparedStatement psInsertCustomer = aConnection.prepareStatement("INSERT INTO students (cusid, unit, address, city,postalcode) VALUES (?,?,?,?,?)");
                psInsertCustomer.setLong(1, id);
                psInsertCustomer.setString(2, unit);
                psInsertCustomer.setString(3, address);
                psInsertCustomer.setString(4, city);
                psInsertCustomer.setString(5, postalCode);

                if (psInsertCustomer.executeUpdate() == 1) {
                    inserted = true;
                    aConnection.commit();
                } else {
                    aConnection.rollback();
                }
                aConnection.setAutoCommit(true);
            }
        }catch(SQLException se){se.printStackTrace();}
        return inserted;
    }

    /**
     *Updates the selected record.
     * @param aCustomer;
     * @return int;
     * @throws NotFoundException;
     */
    public static int update(Customer aCustomer) throws NotFoundException
    {
        int records = 0;

        id = aCustomer.getId();
        unit = aCustomer.getUnit();
        address = aCustomer.getAddress();
        city = aCustomer.getCity();
        postalCode = aCustomer.getPostalCode();


        // NotFoundException is thrown by find method
        try
        {
            Customer.retrieve(id);  //determine if there is a Customer record to be updated
            // if found, execute the SQL update statement
            PreparedStatement psInsertCustomer = aConnection.prepareStatement("UPDATE Customer SET unit = ?,address = ?, city = ?,postalcode = ? WHERE cusid = ?");
            psInsertCustomer.setLong(1, id);
            psInsertCustomer.setString(2, unit);
            psInsertCustomer.setString(3, address);
            psInsertCustomer.setString(4, city);
            psInsertCustomer.setString(5, postalCode);


        }catch(NotFoundException e)
        {
            throw new NotFoundException("Customer with id " + id  + " cannot be updated, does not exist in the system.");
        }catch (SQLException e)
        { System.out.println(e);}
        return records;

    }

    /**
     * Deletes the selected record.
     * @param aCustomer;
     * @return int
     * @throws NotFoundException if the data record is not found in the database
     */
    public static int delete(Customer aCustomer) throws NotFoundException
    {
        int records = 0;
        // retrieve the id (key)
        id = aCustomer.getId();
        // create the SQL delete statement
        String sqlDeleteUsers = "DELETE FROM users " +
                "WHERE Id = '" + id +"'";

        String sqlDeleteCustomer = "DELETE FROM customer " +
                "WHERE cusid = '" + id +"'";
        String sqlDeleteBill = "DELETE FROM bill " +
                "WHERE cusid = '" + id +"'";
        // see if this Customer already exists in the database
        try
        {
            Customer.retrieve(id);  //used to determine if record exists for the passed Customer
            // if found, execute the SQL update statement
            records = aStatement.executeUpdate(sqlDeleteBill);
            records = aStatement.executeUpdate(sqlDeleteCustomer);
            records = aStatement.executeUpdate(sqlDeleteUsers);

        }catch(NotFoundException e)
        {
            throw new NotFoundException("Customer with id " + id
                    + " cannot be deleted, does not exist.");
        }catch (SQLException e)
        { System.out.println(e);	}
        return records;
    }

    /**
     * Checking the user's information using the user provided id and password.
     * @param id;
     * @param password;
     * @return Student;
     * @throws NotFoundException;
     */
    public static Customer authenticate(long id,String password) throws NotFoundException
    {
        aCustomer = null;

        try {
            PreparedStatement psRetrieve = aConnection.prepareStatement("SELECT users.Id, Password, FirstName, LastName, EmailAddress, LastAccess,EnrolDate, Enable, Type, unit, address, city, postalcode FROM users, Customer WHERE users.id = Customer.cusid AND users.Id = ? AND users.password = ?;");
            psRetrieve.setLong(1,id);
            psRetrieve.setString(2,Digest(password,"sha1"));
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
                unit = rs.getString("unit");
                address = rs.getString("address");
                city = rs.getString("city");
                postalCode = rs.getString("postalcode");
                enabled = rs.getBoolean("Enable");
                type = rs.getString("Type").charAt(0);

                try {
                    aCustomer = new Customer(id, password, firstName, lastName, emailAddress, enrolDate, lastAccess, enabled, type, unit, address, city,postalCode);
                } catch (InvalidUserDataException e) {
                    System.out.println("Your record Contains some invalid Data.");
                }
            } else {
                throw new NotFoundException();
            }
            rs.close();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        return aCustomer;
    }

    /**
     * Checking the user's information using the id
     * @param id;
     * @return boolean;
     * @throws SQLException;
     */
    public static boolean isExistingLogin(Long id) throws SQLException {
        // retrieve Customer data
        // define the SQL query statement using the id key
        //System.out.println(sqlQuery);
        PreparedStatement psRetrieve = aConnection.prepareStatement("SELECT * FROM users WHERE id = ?;");
        psRetrieve.setLong(1,id);
        psRetrieve.execute();
        boolean exists = true;
        // execute the SQL query statement
        try
        {
            ResultSet rs = aStatement.executeQuery(String.valueOf(psRetrieve));
            exists = rs.next();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        return exists;
    }
}
