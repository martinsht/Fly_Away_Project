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

    public static User login(String username, String password, String query, String id) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
        ) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (hashedPassword.startsWith("$2a$")) {
                        if (BCrypt.checkpw(password, hashedPassword)) {
                            user = new User(rs.getString("username"), rs.getString("name"), rs.getInt(id));
                        }
                    }else if (password.equals(hashedPassword)) {
                        // You might want to upgrade this password to BCrypt here
                        user = new User(rs.getString("username"), rs.getString("name"), rs.getInt(id));

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
    public static boolean book (String destination, String passpord_id, int num, int id, String name) {
        String query = "Select flight_id from management.flight where arrival = '" + destination + "'";
        String flight_id ="";
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
            pstmt.setInt(1, id);  // Set first name
            pstmt.setString(2, flight_id);
            pstmt.setString(3, destination);
            pstmt.setString(4, passpord_id);
            pstmt.setInt(5, num);



            // Execute the query and get the number of rows affected
            int rowsAffected = pstmt.executeUpdate();

            // If the insertion is successful, commit the transaction
            if (rowsAffected > 0) {
                connection.commit();
                System.out.println("Ticket created for person: " + name);
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
    public static ArrayList<String[]> executeQuery(String query) {
        ArrayList<String[]> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // ResultSetMetaData object provides detailed information about the columns in the result set.
            // This includes column names, types, and other attributes like whether a column is nullable, its size, etc.
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // to add colums automatically
            for (int i = 1; i <= columnCount; i++) {
                Ticket_info.model.addColumn(metaData.getColumnName(i)); // model from form1.java
            }

            // to add rows.
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getString(i + 1);
                }
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return results;
    }
}


