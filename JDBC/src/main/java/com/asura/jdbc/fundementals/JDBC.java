package com.asura.jdbc.fundementals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Dheeraj Kumar Reddy Kotha <dheerajkumarreddykotha@gmail.com>
 */
public class JDBC {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "hr");

        System.out.println("Connection established successfully");
//        Statement statement = conn.createStatement();
//        ResultSet resultset = statement.execute("select ");

        
    }
}
/*
Notes:
1. JDBC is an API that provides connectivity between java application and database.
2. JDBC Driver class converts java calls to database and database calls to java.
3. These drivers are different for different databases.
4. Statement object is used to carry the query from java application to database.
5. The output after executing statement object is known as result, which is a result set.

SETUP:
Step 1: Load an Register JDBC Driver
Loading: class.forName("oracle.jdbc.OracleDriver"); ----> This oracle driver is present in ojdbc.jar
Registering: Inside OracleDriver class we have a static method which registers the driver.

Step 2: Establish connection
Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Xe","user","password");
From java 6, we do not need step 1, because while performing step 2, based on url driver class will be loaded.

Step 3: Preparing statement to carry  request to database
Statement st = conn.createStatement();

Step 4: Executing query and Assigning result
ResultSet rs = st.execute("sql query");

Step 5: Processing result set
while(rs.next()){
    rs.getString(item);

}

Step 6: Close the connection
conn.close();

Driver Manager: This class keeps track of the list of drivers available and handles establishing connection between database and appropriate driver.
Driver:  This interface handles the communications with the database server. 
Statement: It is an object to carry information to the database
Resultset: It is an object which holds the data retreived from the database.

*/