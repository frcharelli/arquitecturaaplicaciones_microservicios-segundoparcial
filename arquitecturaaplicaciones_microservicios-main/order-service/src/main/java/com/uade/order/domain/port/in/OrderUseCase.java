package com.uade.order.domain.port.in;

import com.uade.order.domain.model.Order;

public interface OrderUseCase {
    Order createOrder(Order order);
}
