import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.sql.Connection;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
                        user = new User(rs.getString("username"), rs.getString("name"),rs.getInt("customer_id"));
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
    public static ArrayList<String> destinations() {
        ArrayList<String> dest_list = new ArrayList<String>();
        String query = "Select arrival from management.flight";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dest_list.add(rs.getString("arrival"));
                }
            }
            return dest_list;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return dest_list;
    }
    public static boolean book (String destination, String passpord_id, int num){
        String query = "Select flight_id from management.flight where arrival = "+destination;
        String flight_id;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    flight_id = rs.getString("flight_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        query = "INSERT INTO management.ticket (customer_id, flight_id, destination, passport_id, num_lug) VALUES (?, ?, ?, ? ,?)";

        return false;
    }
}


