package edu.neu.csye7374.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.neu.csye7374.model.Employee;
import edu.neu.csye7374.repository.EmployeeRepository;
import edu.neu.csye7374.designpattern.strategy.EmployeeStrategy;
import edu.neu.csye7374.designpattern.strategy.InventoryStrategy;

/**
 * @apiNote - REST Controller for Employees
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepo;

	@GetMapping("/getAll")
	public List<Employee> getAll() {
		return employeeRepo.getEmployees();
	}

	@GetMapping("/getEmployee/{id}")
	public Employee getEmployee(@PathVariable int id) {
		return employeeRepo.getEmployeebyID(id);
	}

	@PutMapping("/update")
	public void update(@RequestBody Employee employee) {
		InventoryStrategy strategy = new InventoryStrategy(new EmployeeStrategy(employeeRepo, employee));
		strategy.executeUpdate();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deletebyID(@PathVariable int id) {
		InventoryStrategy strategy = new InventoryStrategy(new EmployeeStrategy(employeeRepo, id));
		strategy.executeDelete();
	}

	@PostMapping("/save")
	public void save(@RequestBody Employee employee) {
		InventoryStrategy strategy = new InventoryStrategy(new EmployeeStrategy(employeeRepo, employee));
		strategy.executeAdd();
	}

	@PostMapping("/login")
	public Employee login(@RequestBody JSONObject jsoncredentials) {
		String username = (String) jsoncredentials.get("username");
		String password = (String) jsoncredentials.get("password");
		if(!employeeRepo.usernameExists(username))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username does not exist");
		return employeeRepo.getEmployeeforLogin(username, password);
	}
	
	@PostMapping("/register")
	public JSONObject register(@RequestBody JSONObject registrationData) {
		try {
			String username = (String) registrationData.get("username");
			String password = (String) registrationData.get("password");
			String fullName = (String) registrationData.get("fullName");
			String designation = (String) registrationData.get("designation");
			
			// Validate required fields
			if (username == null || username.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
			}
			if (password == null || password.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
			}
			if (fullName == null || fullName.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full name is required");
			}
			if (designation == null || designation.trim().isEmpty()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Designation is required");
			}
			
			// Check if username already exists
			if (employeeRepo.usernameExists(username)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
			}
			
			// Create new employee
			Employee newEmployee = new Employee();
			newEmployee.setUsername(username.trim());
			newEmployee.setPassword(password); // In production, you should hash this
			newEmployee.setFullName(fullName.trim());
			newEmployee.setDesignation(designation.trim());
			
			// Set default values for other fields
			newEmployee.setSalary(0.0);
			newEmployee.setDob("N/A");
			newEmployee.setRating(0.0);
			
			// Save the employee
			employeeRepo.saveEmployee(newEmployee);
			
			// Return success response
			JSONObject response = new JSONObject();
			response.put("success", Boolean.TRUE);
			response.put("message", "Registration successful");
			return response;
			
		} catch (ResponseStatusException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed: " + e.getMessage());
		}
	}
	
}
