import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.sql.Connection;
import javax.swing.*;
import java.sql.*;


public class connect {
    private static final String URL = "jdbc:mysql://localhost:3306/management";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static User login(String username, String password, String query) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        user = new User(rs.getString("username"), rs.getString("name"));
                    }
                }
            }
            return user;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return user;
    }
    public static boolean add (String name, String username, String password, String query){
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Disable auto-commit to manually handle transactions
            connection.setAutoCommit(false);

            // Prepare the SQL query
            pstmt = connection.prepareStatement(query);

            // Set the values for the query placeholders using the input data
            pstmt.setString(1, name);  // Set first name
            pstmt.setString(2, username);   // Set last name
            pstmt.setString(3, password); // Set the hashed password

            // Execute the query and get the number of rows affected
            int rowsAffected = pstmt.executeUpdate();

            // If the insertion is successful, commit the transaction
            if (rowsAffected > 0) {
                connection.commit();
                System.out.println("Insertion committed successfully for person: " + name);
                return true;
            } else {
                // If insertion fails, roll back the transaction
                connection.rollback();
            }
        } catch (SQLException ex) {
            // Handle SQL exceptions
            try {
                if (connection == null) {
                    // If an error occurs, roll back the transaction
                    connection.rollback();
                }
                System.out.println("SQL Error: " + ex.getMessage());
            } catch (SQLException rollbackEx) {
                // Print stack trace if there is an error during rollback
                rollbackEx.printStackTrace();
            }
        }
        return false;
    }
}


