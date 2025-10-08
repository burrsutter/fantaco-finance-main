package com.fantaco.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FindLostReceiptRequest {
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    // Constructors
    public FindLostReceiptRequest() {}
    
    public FindLostReceiptRequest(String customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
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
}
