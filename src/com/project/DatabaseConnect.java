package com.project;
/*
 *DatabaseConnect - Used to manage database Connectivity(Opening and closing connections)
 * @author Rut Patel
 * @since 1.0
 */

import java.sql.*;

public class DatabaseConnect
{
    /**
     * Database location
     */
    static String url = "jdbc:postgresql://127.0.0.1:5432/db_ems";

    /**
     * Connection object to open port to db
     */
    static Connection aConnection;

    /**
     * Database User
     */
    static String user = "db_ems";

    /**
     * Database user password
     */
    static String password = "db_password";

    /**
     * establishes the database connection
     * @return Connection to the webd4201example_db database
     */
    public static Connection initialize()
    {
        try
        {
            Class.forName("org.postgresql.Driver"); // loads the JDBC Driver for PostGreSQL
            aConnection = DriverManager.getConnection(url, user, password); // creates the database connection instance

        }
        catch (ClassNotFoundException e)  //will occur if you did not import the PostGreSQL *.jar file with drivers
        {
            System.out.println(e);
        }
        catch (SQLException e)	//any other database exception (misnamed db, misnamed user, wrong password, etc)
        {
            System.out.println(e);
        }
        return aConnection;
    }

    /**
     * closes the database connection
     */
    public static void terminate()
    {
        try
        {
            aConnection.close();
        }
        catch (SQLException e)
        { System.out.println(e);	}
    }
}
