package edu.neu.csye7374;

import edu.neu.csye7374.repository.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Yash Zaveri
 * 
 */

@SpringBootApplication
public class Driver {
	public static void main(String[] args) {
		System.out.println("============Main Execution Start===================\n\n");

		// Single line call to run the complete Inventory Management System demo
		new UserRepository().runInventoryManagementDemo();
		 
		System.out.println("\n\n============Main Execution End===================");
	}

}
