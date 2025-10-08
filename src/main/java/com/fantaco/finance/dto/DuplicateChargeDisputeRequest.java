package com.fantaco.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DuplicateChargeDisputeRequest {
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    private String reason;
    
    // Constructors
    public DuplicateChargeDisputeRequest() {}
    
    public DuplicateChargeDisputeRequest(String customerId, Long orderId, String description) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.description = description;
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
}
