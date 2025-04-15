import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/HospitalManagementSystem?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connection;

    public DatabaseHelper() {
        loadDriver();
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }

    public ResultSet executeQuery(String query, Object... params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    public int executeUpdate(String query, Object... params) {
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC Driver: " + e.getMessage());
        }
    }
}
