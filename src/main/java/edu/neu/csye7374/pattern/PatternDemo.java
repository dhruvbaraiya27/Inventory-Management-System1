package edu.neu.csye7374.pattern;

import edu.neu.csye7374.model.Customer;
import edu.neu.csye7374.model.ItemPurchaseOrder;
import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.pattern.command.Command;
import edu.neu.csye7374.pattern.command.CommandInvoker;
import edu.neu.csye7374.pattern.command.RequestClient;
import edu.neu.csye7374.pattern.command.ResponseServer;
import edu.neu.csye7374.pattern.decorator.InventoryDecorator;
import edu.neu.csye7374.pattern.decorator.InventoryProduct;
import edu.neu.csye7374.pattern.decorator.ProductDecorator;
import edu.neu.csye7374.pattern.facade.MessageSender;
import edu.neu.csye7374.pattern.facade.PDFService;
import edu.neu.csye7374.pattern.factory.CommFactory;
import edu.neu.csye7374.pattern.observer.CustomerObserver;
import edu.neu.csye7374.pattern.observer.Observer;
import edu.neu.csye7374.pattern.observer.ProductNotifier;
import edu.neu.csye7374.pattern.observer.UpdateStockDatabase;
import edu.neu.csye7374.pattern.state.StockAlert;
import edu.neu.csye7374.pattern.state.StockContext;
import edu.neu.csye7374.pattern.state.StockState;
import edu.neu.csye7374.pattern.state.StockUpdate;
import edu.neu.csye7374.repository.CustomerRepository;
import edu.neu.csye7374.repository.ItemRepository;

import java.util.List;

/**
 * Pattern Demo Class
 * Demonstrates the Command, Decorator, Facade, and Factory patterns implementation
 */
public class PatternDemo {

    // Dummy ItemRepository for testing without Hibernate
    ItemRepository itemRepo = new ItemRepository() {
        @Override
        public boolean itemExists(String itemName) {
            System.out.println("[MockRepo] Checking if item exists: " + itemName);
            return false; // Assume item doesn't exist for demo
        }

        @Override
        public void update(Item item) {
            System.out.println("[MockRepo] Updating item: " + item.getItemName() + ", stock: " + item.getItemQuantity());
        }

        @Override
        public void save(Item item) {
            System.out.println("[MockRepo] Saving item: " + item.getItemName());
        }
    };

    // Dummy CustomerRepository for testing without Hibernate
    CustomerRepository customerRepo = new CustomerRepository() {
        @Override
        public List<Customer> getCustomers() {
            Customer mockCustomer = new Customer();
            mockCustomer.setCustomerOwnerName("Dhruv");
            return List.of(mockCustomer); // Return dummy customer list
        }
    };
    
    public static void runCommandPatternDemo() {
        System.out.println("\n=== COMMAND PATTERN DEMO ===");
        System.out.println("Command Pattern: Encapsulates a request as an object, allowing you to parameterize clients with different requests.");
        
        try {
            // Create command invoker
            CommandInvoker invoker = new CommandInvoker();
            
            // Create commands
            ResponseServer serverCommand = new ResponseServer();
            RequestClient clientCommand = new RequestClient("Hello from Command Pattern Demo!");
            
            // Add commands to invoker
            invoker.takeOperation(serverCommand);
            invoker.takeOperation(clientCommand);
            
            // Execute all commands
            System.out.println("Executing commands...");
            invoker.executeOperation();
            
            System.out.println("Command Pattern Demo completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Command Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void runDecoratorPatternDemo() {
        System.out.println("\n=== DECORATOR PATTERN DEMO ===");
        System.out.println("Decorator Pattern: Attaches additional responsibilities to an object dynamically.");
        
        try {
            // Create base product
            InventoryProduct baseProduct = new InventoryProduct(25.0);
            System.out.println("Base product cost: $" + baseProduct.getCost());
            
            			// Create item and item purchase order
			Item item = new Item();
			item.setItemPrice(15.0);
			
			ItemPurchaseOrder itemPurchaseOrder = new ItemPurchaseOrder();
			itemPurchaseOrder.setItemPurchaseOrderQuantity(3);
			itemPurchaseOrder.setItemPurchaseOrderProduct(item);
			
			System.out.println("Item purchase order - Quantity: " + itemPurchaseOrder.getItemPurchaseOrderQuantity() + ", Price: $" + item.getItemPrice());
			
			// Create decorated product
			ProductDecorator decoratedProduct = new ProductDecorator(baseProduct, baseProduct, itemPurchaseOrder);
            System.out.println("Decorated product cost: $" + decoratedProduct.getCost());
            
            			// Calculate additional cost
			double additionalCost = itemPurchaseOrder.getItemPurchaseOrderQuantity() * item.getItemPrice();
			System.out.println("Additional cost from decoration: $" + additionalCost);
			System.out.println("Total decorated cost: $" + (baseProduct.getCost() + additionalCost));
            
            System.out.println("Decorator Pattern Demo completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Decorator Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void runFacadePatternDemo() {
        System.out.println("\n=== FACADE PATTERN DEMO ===");
        System.out.println("Facade Pattern: Provides a unified interface to a set of interfaces in a subsystem.");
        
        try {
            // Test Message Sender Facade
            System.out.println("Testing Message Sender Facade...");
            MessageSender.send("Hello from Facade Pattern Demo!");
            
            // Test PDF Service Facade
            System.out.println("Testing PDF Service Facade...");
            ItemPurchaseOrder testOrder = new ItemPurchaseOrder();
            testOrder.setItemPurchaseOrderId(12345);
            
            Item testItem = new Item();
            testItem.setItemPrice(29.99);
            testItem.setItemName("Test Product");
            testOrder.setItemPurchaseOrderProduct(testItem);
            testOrder.setItemPurchaseOrderQuantity(2);
            
            PDFService.generate(testOrder.getItemPurchaseOrderId(), testOrder);
            System.out.println("PDF generated successfully for order ID: " + testOrder.getItemPurchaseOrderId());
            
            System.out.println("Facade Pattern Demo completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Facade Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void runFactoryPatternDemo() {
        System.out.println("\n=== FACTORY PATTERN DEMO ===");
        System.out.println("Factory Pattern: Creates objects without specifying the exact class of object that will be created.");
        
        try {
            // Test Factory Pattern with CommFactory
            System.out.println("Testing Factory Pattern with CommFactory...");
            
            CommFactory factory = new CommFactory();
            
            // Get first object (should create new instance)
            System.out.println("Creating first command object...");
            Object command1 = factory.getObject();
            if (command1 instanceof Command) {
                ((Command) command1).execute();
            }
            
            // Get second object (should return same instance due to lazy singleton)
            System.out.println("Creating second command object...");
            Object command2 = factory.getObject();
            if(command2 instanceof Command) {
                ((Command) command2).execute();

            }

            
            // Verify if it's the same instance
            System.out.println("Are both objects the same instance? " + (command1 == command2));
            
            System.out.println("Factory Pattern Demo completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Factory Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void runObserverPatternDemo() {
        System.out.println("\n=== OBSERVER PATTERN DEMO ===");
        System.out.println("Observer Pattern: Defines a one-to-many dependency between objects...");

        try {
            // Dummy Repositories
            CustomerRepository customerRepo = new CustomerRepository() {
                @Override
                public List<Customer> getCustomers() {
                    Customer c = new Customer();
                    c.setCustomerOwnerName("Dhruv");
                    return List.of(c);
                }
            };

            ItemRepository itemRepo = new ItemRepository() {
                @Override
                public boolean itemExists(String itemName) {
                    return false;
                }

                @Override
                public void save(Item item) {
                    System.out.println("Mock save for item: " + item.getItemName());
                }
            };

            ProductNotifier notifier = new ProductNotifier();
            new CustomerObserver(notifier, customerRepo);
            new UpdateStockDatabase(notifier, itemRepo);

            Item newItem = new Item();
            newItem.setItemName("Premium Bluetooth Headphones");

            System.out.println("Notifying observers of new product: " + newItem.getItemName());
            notifier.setState(newItem);

        } catch (Exception e) {
            System.err.println("Error in Observer Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void runStatePatternDemo() {
        System.out.println("\n=== STATE PATTERN DEMO ===");
        System.out.println("State Pattern: Allows an object to alter its behavior when its internal state changes.");

        try {
            Item item = new Item();
            item.setItemName("Wireless Mouse");

            ItemRepository itemRepo = new ItemRepository() {
                @Override
                public void update(Item i) {
                    System.out.println("Mock update for item: " + i.getItemName() + ", new stock: " + i.getItemQuantity());
                }
            };

            StockContext context = new StockContext();

            StockState stockUpdate = new StockUpdate(item, itemRepo);
            StockState lowStockAlert = new StockAlert(item, itemRepo);

            System.out.println("Updating inventory stock to 100...");
            context.setState(stockUpdate);
            context.getState().action(context, 100);

            System.out.println("Simulating low stock alert...");
            context.setState(lowStockAlert);
            context.getState().action(context, 3);

        } catch (Exception e) {
            System.err.println("Error in State Pattern Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void runNetworkDemo() {
        System.out.println("\n=== NETWORK COMMUNICATION DEMO ===");
        System.out.println("Demonstrating network communication using Command Pattern");
        
        try {
            // Create command invoker for network demo
            CommandInvoker networkInvoker = new CommandInvoker();
            
            // This will trigger the server-client communication
            networkInvoker.triggerServerClient("Sample message from Pattern Demo");
            
            System.out.println("Network Demo completed!");
            
        } catch (Exception e) {
            System.err.println("Error in Network Demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void runCompleteDemo() {
        System.out.println("============ PATTERN DEMO START ============");
        System.out.println("This demo showcases the Command, Decorator, Facade, and Factory patterns implementation");
        
        // Run Command Pattern Demo
        runCommandPatternDemo();
        
        // Run Decorator Pattern Demo
        runDecoratorPatternDemo();
        
        // Run Facade Pattern Demo
        runFacadePatternDemo();
        
            // Run Factory Pattern Demo
            runFactoryPatternDemo();

            // Run Observer Pattern Demo
            runObserverPatternDemo();

            // Run State Pattern Demo
            runStatePatternDemo();
        
        // Run Network Demo (optional - may require network setup)
        // runNetworkDemo();
        
        System.out.println("\n============ PATTERN DEMO END ============");
    }
    
    public static void main(String[] args) {
        runCompleteDemo();
    }
} 