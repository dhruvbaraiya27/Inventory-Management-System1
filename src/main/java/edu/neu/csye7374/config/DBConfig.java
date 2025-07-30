package edu.neu.csye7374.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database Configuration class for MySQL connection
 * Manages database connectivity for the Inventory Management system
 */
public class DBConfig {
    
    private static final String PROPERTIES_FILE = "application.properties";
    private static Properties properties;
    private static DBConfig instance;
    
    // Database connection properties
    private String url;
    private String username;
    private String password;
    private String driver;
    
    /**
     * Private constructor for Singleton pattern
     */
    private DBConfig() {
        loadProperties();
        initializeConnectionProperties();
    }
    
    /**
     * Get singleton instance of DBConfig
     * @return DBConfig instance
     */
    public static synchronized DBConfig getInstance() {
        if (instance == null) {
            instance = new DBConfig();
        }
        return instance;
    }
    
    /**
     * Load properties from application.properties file
     */
    private void loadProperties() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file: " + e.getMessage(), e);
        }
    }
    
    /**
     * Initialize database connection properties from loaded properties
     */
    private void initializeConnectionProperties() {
        this.url = properties.getProperty("db.url");
        this.username = properties.getProperty("db.username");
        this.password = properties.getProperty("db.password");
        this.driver = properties.getProperty("db.driver");
        
        // Validate required properties
        if (url == null || username == null || password == null || driver == null) {
            throw new RuntimeException("Missing required database properties in " + PROPERTIES_FILE);
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + driver, e);
        }
    }
    
    /**
     * Test database connection
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    // Getters for connection properties
    public String getUrl() {
        return url;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getDriver() {
        return driver;
    }
}
