package com.project;


import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class tester {

    public static void main(String[] args) {
        System.out.println("******************** Lab 2 Output ********************\n");
        Connection c = null;
        Customer mainStudent;  //object for a program created Student
        Customer dbStudent;   //object for database retrieved Student
        long possibleId = 101L;
        GregorianCalendar cal = new GregorianCalendar();
        Date lastAccess = cal.getTime();
        cal.set(2017, Calendar.SEPTEMBER, 3);
        Date enrol = cal.getTime();
        try{
            mainStudent = new Customer();
            dbStudent = new Customer();
            System.out.println("\nCreate a Student user to insert/delete later in the program, passing:\n\t" +
                    "Student student1 = new Student(" + possibleId + "L, \"password\", \"Robert\", \"McReady\"," +
                    " \"bob.mcready@dcmail.ca\", enrol, lastAccess, 's', true, \"CPA\", \"Computer Programmer Analyst\", 3);\n");

            mainStudent = new Customer(possibleId,"password", "Robert", "McReady", "bob.mcready@dcmail.ca",
                    enrol, lastAccess,true,'s',"165B","1500 Fallowfield Drive","A1B2C3","Ottawa");
            //mainStudent.dump();
            try{

                // initialize the database (i.e. create a database connection)
                c = DatabaseConnect.initialize();
                Customer.initialize(c);
                User.initialize(c);

                try // attempt to get a Student that does NOT exist, throws Exception
                {
                    System.out.println("\nAttempt to retrieve a student that does not exist (Id: " + possibleId + ")");
                    dbStudent = Customer.retrieve(possibleId);
                    System.out.println("Student record with id " + possibleId + " retrieved from the database\n");
                    dbStudent.dump();
                }
                catch(NotFoundException e)
                {	System.out.println(e.getMessage());}

                try // attempt to get a Student that does exist
                {
                    possibleId = 100111111L;
                    System.out.println("\nAttempt to retrieve a student that does exist (Id: " + possibleId + ")");
                    dbStudent = Customer.retrieve(possibleId);
                    System.out.println("Student record with id " + possibleId + " retrieved from the database\n");
                    //dbStudent.dump();
                }
                catch(NotFoundException e)
                {	System.out.println(e.getMessage());}

                try
                {
                    System.out.println("\nAttempt to insert a new student record for "
                            + mainStudent.getFirstName() + " " + mainStudent.getLastName()
                            + "(Id: " + mainStudent.getId()+")");
                    mainStudent.create();
                    System.out.println("Student record added to the database.\n");
                }
                catch(DuplicateException e)
                {	System.out.println(e);}

                try
                {
                    System.out.println("\nChange the student object and attempt to update the student record for "
                            + mainStudent.getFirstName() + " " + mainStudent.getLastName()
                            + "(Id: " + mainStudent.getId() +")");
                    mainStudent.setPassword("newpassword");

                    mainStudent.update();
                    System.out.println("Student record updated in the database.\n");
                }
                catch(NotFoundException e)
                {	System.out.println(e);}

                System.out.println("\nStudents are encouraged to comment out the folowing try...catch block to"
                        + " verify the new record exists in pgAdmin by running the \"SELECT * FROM Students;\" command ");

                try // now, attempt to delete the new Student
                {
                    System.out.println("\nAttempt to delete the new student record for "
                            + mainStudent.getFirstName() + " " + mainStudent.getLastName()
                            + "(Id: " + mainStudent.getId() + ")");
                    mainStudent.delete();
                    System.out.println("Student record with id " + mainStudent.getId() + " successfully removed from the database.\n");
                }
                catch(NotFoundException e)
                {	System.out.println(e);}

                try // now, try to find the deleted Student
                {
                    possibleId = 100222222L;
                    mainStudent = Customer.retrieve(possibleId);
                    mainStudent.dump();
                    mainStudent.delete();
                }
                catch(NotFoundException e)
                {
                    System.out.println("Did not find student record with id " + possibleId + ".\n");
                }
                /*****************************MY STUDENT RECORD*******************************/
                try // attempt to get a Student that does exist
                {
                    possibleId = 100774814L;
                    System.out.println("\nRetriving my Student Record (Id: " + possibleId + ")");
                    dbStudent = Customer.retrieve(possibleId);
                    dbStudent.dump();
                }
                catch(NotFoundException e)
                {	System.out.println(e.getMessage());}
                /***********************************************************************/
            }catch(Exception e){   //catch for database initialize/connect try
                System.out.println(e.toString());
            }finally{ // close the database resources, if possible
                try{  Customer.terminate(); }catch(Exception e){}
                try{  DatabaseConnect.terminate(); }catch(Exception e){}
            }
        }catch(InvalidUserDataException iude){
            System.out.println(iude.getMessage());
        }
    }
}

