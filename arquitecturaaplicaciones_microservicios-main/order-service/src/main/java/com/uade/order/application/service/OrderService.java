package com.uade.order.application.service;

import com.uade.order.domain.model.Order;
import com.uade.order.domain.port.in.OrderUseCase;
import com.uade.order.domain.port.out.EventPublisherPort;
import com.uade.order.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderUseCase {
    private final OrderRepositoryPort orderRepository;
    private final EventPublisherPort eventPublisher;

    public OrderService(OrderRepositoryPort orderRepository, EventPublisherPort eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);
        eventPublisher.publishOrderCreatedEvent(savedOrder.getId(), savedOrder.getProductId());
        return savedOrder;
    }
}
