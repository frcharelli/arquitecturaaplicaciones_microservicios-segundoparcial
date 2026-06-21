package com.uade.order.domain.event;

public class OrderCreatedEvent {
    private Long orderId;
    private String productId;
    
    public OrderCreatedEvent() {}
    public OrderCreatedEvent(Long orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
}
