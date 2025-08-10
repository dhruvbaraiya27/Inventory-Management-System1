package edu.neu.csye7374.controller;

import edu.neu.csye7374.repository.ProductRepository;
import edu.neu.csye7374.repository.BuyerRepository;
import edu.neu.csye7374.repository.OrderRepository;
import edu.neu.csye7374.repository.InvoiceRepository;
import edu.neu.csye7374.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @apiNote - REST Controller for Dashboard Statistics
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private BuyerRepository buyerRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private InvoiceRepository invoiceRepo;

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        try {
            // Get total counts
            int totalProducts = productRepo.getProducts().size();
            int totalBuyers = buyerRepo.getBuyers().size();
            int totalOrders = orderRepo.getPurchaseOrders().size();
            int totalInvoices = invoiceRepo.getInvoices().size();
            
            // Calculate pending orders (unpaid orders)
            List<PurchaseOrder> allOrders = orderRepo.getPurchaseOrders();
            int pendingOrders = (int) allOrders.stream()
                .filter(order -> !order.isPaid())
                .count();
            
            // Calculate total sales (sum of all paid orders)
            double totalSales = allOrders.stream()
                .filter(PurchaseOrder::isPaid)
                .mapToDouble(PurchaseOrder::getTotalAmount)
                .sum();
            
            // Prepare response
            stats.put("totalProducts", totalProducts);
            stats.put("totalBuyers", totalBuyers);
            stats.put("totalOrders", totalOrders);
            stats.put("totalInvoices", totalInvoices);
            stats.put("pendingOrders", pendingOrders);
            stats.put("totalSales", totalSales);
            stats.put("completedOrders", totalOrders - pendingOrders);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Return default values in case of error
            stats.put("totalProducts", 0);
            stats.put("totalBuyers", 0);
            stats.put("totalOrders", 0);
            stats.put("totalInvoices", 0);
            stats.put("pendingOrders", 0);
            stats.put("totalSales", 0.0);
            stats.put("completedOrders", 0);
        }
        
        return stats;
    }
    
    @GetMapping("/recent-activity")
    public List<Map<String, Object>> getRecentActivity() {
        // For now, return static data but this can be extended to track real activities
        List<Map<String, Object>> activities = List.of(
            Map.of("action", "System initialized", "time", "Just now", "type", "info"),
            Map.of("action", "Dashboard loaded", "time", "Few seconds ago", "type", "success")
        );
        
        return activities;
    }
}