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
        statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet resultset = statement.executeQuery("select * from Employees");
        while(resultset.next()){
        	String s = resultset.getString("FIRST_NAME");
        	resultset.updateString("FIRST_NAME", s+"Asura");
        	resultset.updateRow();
        	System.out.println("The first name is: "+resultset.getString("FIRST_NAME"));
        	System.out.println("The firstname indes is: "+resultset.getInt(1));
        	resultset.relative(3);
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
13. Cursor is used to access the result set. In general it is a pointer which is always on top of the result set.
14. This cursor calls resultset.next() to check for data in result set and it returns false when it is after the last row.
15. The result set interface provides methods for retrieving and manipulating the executed queries.
16. The characteristics are type, concurrency and cursor holdability.
17. Types of Result sets:
	a) TYPE_FORWARD_ONLY - The result set cannot be scrolled. Its cursor can move forward direction only.
	b) TYPE_SCROLL_INSENSITIVE - The result set can be scrolled. It can move backward as well as forward. It is insensitive to the data modified after retrieving from data base.
	c) TYPE_SCROLL_SENSITIVE - The result set can be scrolled. It can move both forward and backward. It is sensitive to the data modified in the database. The result set reflects the changes made to data.
18. Not all databases support all types of result sets.  DatabaseMetaData.supportsResultSetType returns true if a result set type is supported.
19. Types of result set concurrency: It determines what level of update functionality is supported.
	a) CONCUR_READ_ONLY - The resultset object cannot be updated
	b) CONCUR_UPDATABLE - The resultset object can be updated.
20. Calling the method Connection.commit closes the result set objects that have been created during current transaction. The ResultSet property holdability gives the application control over whether 
    ResultSet objects (cursors) are closed when commit is called.
	a) HOLD_CURSORS_OVER_COMMIT: ResultSet cursors are not closed; they are holdable: they are held open when the method commit is called. Holdable cursors might be ideal if your application uses mostly 
	   read-only ResultSet objects.
	b) CLOSE_CURSORS_AT_COMMIT: ResultSet objects (cursors) are closed when the commit method is called. Closing cursors when this method is called can result in better performance for some applications.
21. Syntax to update using JDBC:
 Statement stmt = null;
    try {
        stmt = con.createStatement();
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
        ResultSet uprs = stmt.executeQuery(
            "SELECT * FROM " + dbName + ".COFFEES");

        while (uprs.next()) {
            float f = uprs.getFloat("PRICE");
            uprs.updateFloat( "PRICE", f * percentage);
            uprs.updateRow();
        }

    } catch (SQLException e ) {
        JDBCTutorialUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
 22. Syntax to execute a batch statements
 	Statement stmt = null;
    try {
        this.con.setAutoCommit(false);
        stmt = this.con.createStatement();

        stmt.addBatch(
            "INSERT INTO COFFEES " +
            "VALUES('Amaretto', 49, 9.99, 0, 0)");

        stmt.addBatch(
            "INSERT INTO COFFEES " +
            "VALUES('Hazelnut', 49, 9.99, 0, 0)");

        stmt.addBatch(
            "INSERT INTO COFFEES " +
            "VALUES('Amaretto_decaf', 49, " +
            "10.99, 0, 0)");

        stmt.addBatch(
            "INSERT INTO COFFEES " +
            "VALUES('Hazelnut_decaf', 49, " +
            "10.99, 0, 0)");

        int [] updateCounts = stmt.executeBatch();
        this.con.commit();

    } catch(BatchUpdateException b) {
        JDBCTutorialUtilities.printBatchUpdateException(b);
    } catch(SQLException ex) {
        JDBCTutorialUtilities.printSQLException(ex);
    } finally {
        if (stmt != null) { stmt.close(); }
        this.con.setAutoCommit(true);
    }
23. Syntax to insert row into result set
	Statement stmt = null;
    try {
        stmt = con.createStatement(
            ResultSet.TYPE_SCROLL_SENSITIVE
            ResultSet.CONCUR_UPDATABLE);

        ResultSet uprs = stmt.executeQuery(
            "SELECT * FROM " + dbName +
            ".COFFEES");

        uprs.moveToInsertRow();
        uprs.updateString("COF_NAME", coffeeName);
        uprs.updateInt("SUP_ID", supplierID);
        uprs.updateFloat("PRICE", price);
        uprs.updateInt("SALES", sales);
        uprs.updateInt("TOTAL", total);

        uprs.insertRow();
        uprs.beforeFirst();
    } catch (SQLException e ) {
        JDBCTutorialUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
24. A prepared statement is a pre-compiled SQL statements in DBMS. It allows you to send parameters and get outputs. So You can set both IN and OUT parameters.
Syntax:
public void updateCoffeeSales(HashMap<String, Integer> salesForWeek)
    throws SQLException {

    PreparedStatement updateSales = null;
    PreparedStatement updateTotal = null;

    String updateString =
        "update " + dbName + ".COFFEES " +
        "set SALES = ? where COF_NAME = ?";

    String updateStatement =
        "update " + dbName + ".COFFEES " +
        "set TOTAL = TOTAL + ? " +
        "where COF_NAME = ?";

    try {
        con.setAutoCommit(false);
        updateSales = con.prepareStatement(updateString);
        updateTotal = con.prepareStatement(updateStatement);

        for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
            updateSales.setInt(1, e.getValue().intValue());
            updateSales.setString(2, e.getKey());
            updateSales.executeUpdate();
            updateTotal.setInt(1, e.getValue().intValue());
            updateTotal.setString(2, e.getKey());
            updateTotal.executeUpdate();
            con.commit();
        }
    } catch (SQLException e ) {
        JDBCTutorialUtilities.printSQLException(e);
        if (con != null) {
            try {
                System.err.print("Transaction is being rolled back");
                con.rollback();
            } catch(SQLException excep) {
                JDBCTutorialUtilities.printSQLException(excep);
            }
        }
    } finally {
        if (updateSales != null) {
            updateSales.close();
        }
        if (updateTotal != null) {
            updateTotal.close();
        }
        con.setAutoCommit(true);
    }
}
25.  A transaction is a set of one or more statements that is executed as a unit, so either all of the statements are executed, or none of the statements is executed.
26. After the auto-commit mode is disabled, no SQL statements are committed until you call the method commit explicitly. All statements executed after the previous call 
    to the method commit are included in the current transaction and committed together as a unit. 
27. The method Connection.setSavepoint, sets a Savepoint object within the current transaction. The Connection.rollback method is overloaded to take a Savepoint argument.
28. The method Connection.releaseSavepoint takes a Savepoint object as a parameter and removes it from the current transaction.
29. A stored procedure is a group of SQL Statements that perform a particular task.
30. The Callable statements are used to call the stored procedures which has, IN,OUT and IN/OUT parameters.
31. For IN/OUT we need to register before assigning value to it.
32. We can retrieve the result set using getResultSet().
33. BLOB (Binary Large Object) is a binary data stored as a single entity in the database.
34. These are basically images, documents audo etc,.
35. CLOB is a Character Large Objects, collection of character data stored as a single entity in the database.
36. These are used to store large text,XML files. Holds maximum length of 4GB data.
37. JAVA properties file is used to store the properties of the database. Ex: demo.properties
38. This file is loaded using props.load(new FileInputstream(demo.properties));
39. 



*/