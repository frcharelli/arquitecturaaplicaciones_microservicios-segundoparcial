package com.uade.order.domain.port.out;

import com.uade.order.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
}
