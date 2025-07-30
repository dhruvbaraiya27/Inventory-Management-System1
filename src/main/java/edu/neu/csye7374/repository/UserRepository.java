package edu.neu.csye7374.repository;

import edu.neu.csye7374.config.DBConfig;
import edu.neu.csye7374.controller.UserController;
import edu.neu.csye7374.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Repository class for User operations
 * Handles all database operations and business logic for User entities
 */
public class UserRepository {
    
    private final UserController userController;
    private final DBConfig dbConfig;
    
    public UserRepository() {
        this.dbConfig = DBConfig.getInstance();
        this.userController = new UserController();
    }
    
    /**
     * Test database connection
     * @return true if connection is successful
     */
    public boolean testDatabaseConnection() {
        System.out.println("📊 Testing Database Connection...");
        boolean isConnected = dbConfig.testConnection();
        if (isConnected) {
            System.out.println("✅ Database connection successful!");
        } else {
            System.out.println("❌ Database connection failed!");
        }
        return isConnected;
    }
    
    /**
     * Initialize sample data and demonstrate all CRUD operations
     */
    public void runInventoryManagementDemo() {
        try {
            System.out.println("🚀 Starting Inventory Management System Demo...\n");
            
            // Test database connection first
            if (!testDatabaseConnection()) {
                System.err.println("Cannot proceed without database connection!");
                return;
            }
            
            System.out.println("✅ UserController initialized successfully!\n");
            
            // Create sample users
            createSampleUsers();
            
            // Demonstrate all CRUD operations
            demonstrateReadOperations();
            demonstrateUpdateOperations();
            demonstrateUtilityOperations();
            
            System.out.println("🎉 All database operations completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("❌ Database error occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create sample users for demonstration
     */
    private void createSampleUsers() throws SQLException {
        System.out.println("👥 Creating sample users...");
        
        // Check if users already exist to avoid duplicates
        if (userController.getUserCount() > 0) {
            System.out.println("ℹ️ Users already exist in database. Skipping creation...\n");
            return;
        }
        
        User admin = new User();
        admin.setUsername("admin");
        admin.setRole("ADMIN");
        
        User manager = new User();
        manager.setUsername("manager1");
        manager.setRole("MANAGER");
        
        User employee = new User();
        employee.setUsername("employee1");
        employee.setRole("EMPLOYEE");
        
        // Create users in database
        userController.createUser(admin);
        userController.createUser(manager);
        userController.createUser(employee);
        
        System.out.println("✅ Sample users created successfully!\n");
    }
    
    /**
     * Demonstrate read operations
     */
    private void demonstrateReadOperations() throws SQLException {
        System.out.println("📋 === READ OPERATIONS DEMO ===");
        
        // Get all users
        System.out.println("📋 Retrieving all users from database:");
        List<User> allUsers = userController.getAllUsers();
        allUsers.forEach(user -> System.out.println("  - " + user));
        System.out.println();
        
        if (!allUsers.isEmpty()) {
            User firstUser = allUsers.get(0);
            
            // Find user by ID
            System.out.println("🔍 Finding user by ID (ID: " + firstUser.getId() + "):");
            User foundUser = userController.getUserById(firstUser.getId());
            System.out.println("  Found: " + foundUser);
            System.out.println();
            
            // Find user by username
            System.out.println("🔍 Finding user by username (username: " + firstUser.getUsername() + "):");
            User foundByUsername = userController.getUserByUsername(firstUser.getUsername());
            System.out.println("  Found: " + foundByUsername);
            System.out.println();
        }
    }
    
    /**
     * Demonstrate update operations
     */
    private void demonstrateUpdateOperations() throws SQLException {
        System.out.println("✏️ === UPDATE OPERATIONS DEMO ===");
        
        // Find a manager to update
        User managerToUpdate = userController.getUserByUsername("manager1");
        if (managerToUpdate != null) {
            System.out.println("✏️ Updating user role from " + managerToUpdate.getRole() + " to SENIOR_MANAGER...");
            managerToUpdate.setRole("SENIOR_MANAGER");
            User updatedUser = userController.updateUser(managerToUpdate);
            System.out.println("  Updated: " + updatedUser);
            System.out.println();
        }
    }
    
    /**
     * Demonstrate utility operations
     */
    private void demonstrateUtilityOperations() throws SQLException {
        System.out.println("🔧 === UTILITY OPERATIONS DEMO ===");
        
        // Get users by role
        System.out.println("👨‍💼 Finding all ADMIN users:");
        List<User> adminUsers = userController.getUsersByRole("ADMIN");
        adminUsers.forEach(user -> System.out.println("  - " + user));
        System.out.println();
        
        // Check if username exists
        System.out.println("🔍 Checking if username 'admin' exists:");
        boolean exists = userController.usernameExists("admin");
        System.out.println("  Exists: " + exists);
        System.out.println();
        
        // Get total user count
        System.out.println("📊 Total users in database:");
        int totalUsers = userController.getUserCount();
        System.out.println("  Total: " + totalUsers + " users");
        System.out.println();
    }
    
    /**
     * Create a new user
     * @param user User to create
     * @return Created user with ID
     */
    public User createUser(User user) throws SQLException {
        return userController.createUser(user);
    }
    
    /**
     * Get user by ID
     * @param id User ID
     * @return User object or null if not found
     */
    public User getUserById(int id) throws SQLException {
        return userController.getUserById(id);
    }
    
    /**
     * Get user by username
     * @param username Username
     * @return User object or null if not found
     */
    public User getUserByUsername(String username) throws SQLException {
        return userController.getUserByUsername(username);
    }
    
    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() throws SQLException {
        return userController.getAllUsers();
    }
    
    /**
     * Update user
     * @param user User to update
     * @return Updated user
     */
    public User updateUser(User user) throws SQLException {
        return userController.updateUser(user);
    }
    
    /**
     * Delete user by ID
     * @param id User ID
     * @return true if deleted, false if not found
     */
    public boolean deleteUser(int id) throws SQLException {
        return userController.deleteUser(id);
    }
    
    /**
     * Get users by role
     * @param role User role
     * @return List of users with specified role
     */
    public List<User> getUsersByRole(String role) throws SQLException {
        return userController.getUsersByRole(role);
    }
    
    /**
     * Check if username exists
     * @param username Username to check
     * @return true if exists, false otherwise
     */
    public boolean usernameExists(String username) throws SQLException {
        return userController.usernameExists(username);
    }
    
    /**
     * Get total user count
     * @return Total number of users
     */
    public int getUserCount() throws SQLException {
        return userController.getUserCount();
    }
}
