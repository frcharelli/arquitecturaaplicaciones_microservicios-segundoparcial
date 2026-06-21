package com.uade.order.infrastructure.adapter.out.persistence;

import com.uade.order.domain.model.Order;
import com.uade.order.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {
    private final OrderJpaRepository repository;

    public OrderPersistenceAdapter(OrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        if (order.getId() != null) {
            entity.setId(order.getId());
        }
        entity.setProductId(order.getProductId());
        entity.setQuantity(order.getQuantity());
        entity.setStatus(order.getStatus());
        
        OrderJpaEntity saved = repository.save(entity);
        
        order.setId(saved.getId());
        return order;
    }
}
