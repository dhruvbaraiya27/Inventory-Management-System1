package edu.neu.csye7374.controller;

import edu.neu.csye7374.config.DBConfig;
import edu.neu.csye7374.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for User operations
 * Handles CRUD operations for User entities in the database
 */
public class UserController {
    
    private final DBConfig dbConfig;
    private static final String TABLE_NAME = "users";
    
    /**
     * Constructor
     */
    public UserController() {
        this.dbConfig = DBConfig.getInstance();
        createTableIfNotExists();
    }
    
    /**
     * Create users table if it doesn't exist
     */
    private void createTableIfNotExists() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS %s (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(100) NOT NULL UNIQUE,
                    role VARCHAR(50) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                )
                """.formatted(TABLE_NAME);
        
        try (Connection connection = dbConfig.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Users table created or already exists.");
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
            throw new RuntimeException("Failed to create users table", e);
        }
    }
    
    /**
     * Create a new user
     * @param user User object to create
     * @return Created user with generated ID
     * @throws SQLException if database operation fails
     */
    public User createUser(User user) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + " (username, role) VALUES (?, ?)";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRole());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            
            System.out.println("User created successfully: " + user);
            return user;
        }
    }
    
    /**
     * Retrieve a user by ID
     * @param id User ID
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    public User getUserById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
                return null;
            }
        }
    }
    
    /**
     * Retrieve a user by username
     * @param username Username
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    public User getUserByUsername(String username) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            
            statement.setString(1, username);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToUser(resultSet);
                }
                return null;
            }
        }
    }
    
    /**
     * Retrieve all users
     * @return List of all users
     * @throws SQLException if database operation fails
     */
    public List<User> getAllUsers() throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " ORDER BY id";
        List<User> users = new ArrayList<>();
        
        try (Connection connection = dbConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        }
        
        return users;
    }
    
    /**
     * Update an existing user
     * @param user User object with updated information
     * @return Updated user object
     * @throws SQLException if database operation fails
     */
    public User updateUser(User user) throws SQLException {
        String updateSQL = "UPDATE " + TABLE_NAME + " SET username = ?, role = ? WHERE id = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRole());
            statement.setInt(3, user.getId());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected. User might not exist.");
            }
            
            System.out.println("User updated successfully: " + user);
            return user;
        }
    }
    
    /**
     * Delete a user by ID
     * @param id User ID
     * @return true if user was deleted, false if user not found
     * @throws SQLException if database operation fails
     */
    public boolean deleteUser(int id) throws SQLException {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            
            statement.setInt(1, id);
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User with ID " + id + " deleted successfully.");
                return true;
            } else {
                System.out.println("No user found with ID " + id);
                return false;
            }
        }
    }
    
    /**
     * Get users by role
     * @param role User role
     * @return List of users with the specified role
     * @throws SQLException if database operation fails
     */
    public List<User> getUsersByRole(String role) throws SQLException {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE role = ? ORDER BY username";
        List<User> users = new ArrayList<>();
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            
            statement.setString(1, role);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapResultSetToUser(resultSet));
                }
            }
        }
        
        return users;
    }
    
    /**
     * Check if username exists
     * @param username Username to check
     * @return true if username exists, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean usernameExists(String username) throws SQLException {
        String selectSQL = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE username = ?";
        
        try (Connection connection = dbConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSQL)) {
            
            statement.setString(1, username);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
                return false;
            }
        }
    }
    
    /**
     * Get total count of users
     * @return Total number of users
     * @throws SQLException if database operation fails
     */
    public int getUserCount() throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM " + TABLE_NAME;
        
        try (Connection connection = dbConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(countSQL)) {
            
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        }
    }
    
    /**
     * Map ResultSet to User object
     * @param resultSet Database result set
     * @return User object
     * @throws SQLException if database operation fails
     */
    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setRole(resultSet.getString("role"));
        return user;
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public boolean testConnection() {
        return dbConfig.testConnection();
    }
}
