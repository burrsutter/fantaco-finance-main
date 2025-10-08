package com.fantaco.finance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "disputes")
public class Dispute {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(name = "dispute_number", unique = true, nullable = false)
    private String disputeNumber;
    
    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @NotBlank
    @Column(name = "customer_id", nullable = false)
    private String customerId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "dispute_type", nullable = false)
    private DisputeType disputeType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DisputeStatus status;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;
    
    @Column(name = "dispute_date", nullable = false)
    private LocalDateTime disputeDate;
    
    @Column(name = "resolved_date")
    private LocalDateTime resolvedDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Dispute() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Dispute(String disputeNumber, Long orderId, String customerId, DisputeType disputeType, DisputeStatus status) {
        this();
        this.disputeNumber = disputeNumber;
        this.orderId = orderId;
        this.customerId = customerId;
        this.disputeType = disputeType;
        this.status = status;
        this.disputeDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDisputeNumber() {
        return disputeNumber;
    }
    
    public void setDisputeNumber(String disputeNumber) {
        this.disputeNumber = disputeNumber;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public DisputeType getDisputeType() {
        return disputeType;
    }
    
    public void setDisputeType(DisputeType disputeType) {
        this.disputeType = disputeType;
    }
    
    public DisputeStatus getStatus() {
        return status;
    }
    
    public void setStatus(DisputeStatus status) {
        this.status = status;
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
    
    public LocalDateTime getDisputeDate() {
        return disputeDate;
    }
    
    public void setDisputeDate(LocalDateTime disputeDate) {
        this.disputeDate = disputeDate;
    }
    
    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }
    
    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public enum DisputeType {
        DUPLICATE_CHARGE, UNAUTHORIZED_CHARGE, PRODUCT_NOT_RECEIVED, DEFECTIVE_PRODUCT, BILLING_ERROR
    }
    
    public enum DisputeStatus {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED, CANCELLED
    }
}
