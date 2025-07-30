package edu.neu.csye7374.pattern;

import com.inventory.designpattern.command.CommandInvoker;
import com.inventory.designpattern.command.RequestClient;
import com.inventory.designpattern.command.ResponseServer;
import edu.neu.csye7374.model.ItemPurchaseOrder;
import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.pattern.decorator.InventoryDecorator;
import edu.neu.csye7374.pattern.decorator.InventoryProduct;
import edu.neu.csye7374.pattern.decorator.ProductDecorator;

/**
 * Pattern Demo Class
 * Demonstrates the Command and Decorator patterns implementation
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
        System.out.println("This demo showcases the Command and Decorator patterns implementation");
        
        // Run Command Pattern Demo
        runCommandPatternDemo();
        
        // Run Decorator Pattern Demo
        runDecoratorPatternDemo();
        
        // Run Network Demo (optional - may require network setup)
        // runNetworkDemo();
        
        System.out.println("\n============ PATTERN DEMO END ============");
    }
    
    public static void main(String[] args) {
        runCompleteDemo();
    }
} 