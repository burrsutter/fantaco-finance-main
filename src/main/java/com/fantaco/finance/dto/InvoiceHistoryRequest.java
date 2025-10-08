package com.fantaco.finance.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class InvoiceHistoryRequest {
    
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer limit = 50; // Default limit
    
    // Constructors
    public InvoiceHistoryRequest() {}
    
    public InvoiceHistoryRequest(String customerId) {
        this.customerId = customerId;
    }
    
    public InvoiceHistoryRequest(String customerId, LocalDateTime startDate, LocalDateTime endDate) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public Integer getLimit() {
        return limit;
    }
    
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
