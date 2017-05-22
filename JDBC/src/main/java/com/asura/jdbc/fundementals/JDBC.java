package com.asura.jdbc.fundementals;

/**
 *
 * @author Dheeraj Kumar Reddy Kotha <dheerajkumarreddykotha@gmail.com>
 */
public class JDBC {
    public static void main(String[] args) {
        
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
class.forName("oracle.jdbc.OracleDriver"); ----> This oracle driver is present in ojdbc.jar

Step 2: Establish connection
Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Xe","user","password");

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

*/