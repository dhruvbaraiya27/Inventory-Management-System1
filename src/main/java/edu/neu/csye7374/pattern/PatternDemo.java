package edu.neu.csye7374.pattern;

import com.inventory.designpattern.command.CommandInvoker;
import com.inventory.designpattern.command.RequestClient;
import com.inventory.designpattern.command.ResponseServer;
import edu.neu.csye7374.model.ItemPurchaseOrder;
import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.pattern.decorator.InventoryDecorator;
import edu.neu.csye7374.pattern.decorator.InventoryProduct;
import edu.neu.csye7374.pattern.decorator.ProductDecorator;
import edu.neu.csye7374.pattern.facade.MessageSender;
import edu.neu.csye7374.pattern.facade.PDFService;
import edu.neu.csye7374.pattern.factory.CommFactory;

/**
 * Pattern Demo Class
 * Demonstrates the Command, Decorator, Facade, and Factory patterns implementation
 */
public class PatternDemo {
    
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
            var command1 = factory.getObject();
            command1.execute();
            
            // Get second object (should return same instance due to lazy singleton)
            System.out.println("Creating second command object...");
            var command2 = factory.getObject();
            command2.execute();
            
            // Verify if it's the same instance
            System.out.println("Are both objects the same instance? " + (command1 == command2));
            
            System.out.println("Factory Pattern Demo completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error in Factory Pattern Demo: " + e.getMessage());
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
        
        // Run Network Demo (optional - may require network setup)
        // runNetworkDemo();
        
        System.out.println("\n============ PATTERN DEMO END ============");
    }
    
    public static void main(String[] args) {
        runCompleteDemo();
    }
} 