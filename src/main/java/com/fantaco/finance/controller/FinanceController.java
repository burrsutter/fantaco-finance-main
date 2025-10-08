package com.fantaco.finance.controller;

import com.fantaco.finance.dto.*;
import com.fantaco.finance.entity.*;
import com.fantaco.finance.service.FinanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "*")
public class FinanceController {
    
    @Autowired
    private FinanceService financeService;
    
    /**
     * Get order history for a customer
     * POST /api/finance/orders/history
     */
    @PostMapping("/orders/history")
    public ResponseEntity<Map<String, Object>> getOrderHistory(@Valid @RequestBody OrderHistoryRequest request) {
        try {
            List<Order> orders = financeService.getOrderHistory(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Order history retrieved successfully");
            response.put("data", orders);
            response.put("count", orders.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving order history: " + e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Get invoice history for a customer
     * POST /api/finance/invoices/history
     */
    @PostMapping("/invoices/history")
    public ResponseEntity<Map<String, Object>> getInvoiceHistory(@Valid @RequestBody InvoiceHistoryRequest request) {
        try {
            List<Invoice> invoices = financeService.getInvoiceHistory(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Invoice history retrieved successfully");
            response.put("data", invoices);
            response.put("count", invoices.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving invoice history: " + e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Start a duplicate charge dispute
     * POST /api/finance/disputes/duplicate-charge
     */
    @PostMapping("/disputes/duplicate-charge")
    public ResponseEntity<Map<String, Object>> startDuplicateChargeDispute(@Valid @RequestBody DuplicateChargeDisputeRequest request) {
        try {
            Dispute dispute = financeService.startDuplicateChargeDispute(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Duplicate charge dispute started successfully");
            response.put("data", dispute);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error starting duplicate charge dispute: " + e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Find lost receipt for an order
     * POST /api/finance/receipts/find-lost
     */
    @PostMapping("/receipts/find-lost")
    public ResponseEntity<Map<String, Object>> findLostReceipt(@Valid @RequestBody FindLostReceiptRequest request) {
        try {
            Receipt receipt = financeService.findLostReceipt(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Lost receipt found/created successfully");
            response.put("data", receipt);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error finding lost receipt: " + e.getMessage());
            errorResponse.put("data", null);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Health check endpoint
     * GET /api/finance/health
     */
    int count = 0;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        count++;
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Fantaco Finance API");
        response.put("count", count);
        response.put("timestamp", java.time.LocalDateTime.now());
        
        return ResponseEntity.ok(response);
    }
}
