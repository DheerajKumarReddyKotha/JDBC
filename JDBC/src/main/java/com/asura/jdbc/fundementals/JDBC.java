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
        Statement statement = conn.createStatement();
        ResultSet resultset = statement.executeQuery("select * from Employees");
        while(resultset.next()){
        	System.out.println("The first name is: "+resultset.getString("FIRST_NAME"));
        	System.out.println("The firstname indes is: "+resultset.getInt(1));
        }
        
    }
}
/*
Notes:
The javax namespace is usually (that's a loaded word) used for standard extensions, currently known as optional packages.

1. The Java Database Connectivity (JDBC) API provides universal data access from the Java programming language.
2.  Using the JDBC API, you can access virtually any data source, from relational databases to spreadsheets and flat files.

The JDBC API is comprised in two packages:
a) java.sql
b) javax.sql

3. A JDBC driver is a software component enabling a Java application to interact with a database.
4. Driver Manager class is a service that manages the set of JDBC drivers. It loads the registered Drivers in class path while initializing the class.
5. From JDK 8, it loads the suitable driver while creating connection according to jdbc url provided.
6. The DriverManager attempts to select an appropriate driver from the set of registered JDBC drivers to establish a connection.
7. Connection object is created using getConnection method with the jdbc url provided.
8. The statement object is created using the connection object to send SQL statements to database.
9. The Result set is an object which is obtained after executing the query.


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

10. Result set is initially placed before first row in the table.
11. resultset.next() is used to check whether the rows are present, it moves one row forward in the result set obtained.
12. We can read the data using getter methods with column name specified in it or the one-based index.
13. 

*/