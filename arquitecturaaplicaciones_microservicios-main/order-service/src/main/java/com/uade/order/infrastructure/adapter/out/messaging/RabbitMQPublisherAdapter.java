package com.uade.order.infrastructure.adapter.out.messaging;

import com.uade.order.domain.event.OrderCreatedEvent;
import com.uade.order.domain.port.out.EventPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPublisherAdapter implements EventPublisherPort {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQPublisherAdapter.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderCreatedEvent(Long orderId, String productId) {
        OrderCreatedEvent event = new OrderCreatedEvent(orderId, productId);
        log.info("Publicando evento de orden creada: orderId={}, productId={}", orderId, productId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_ORDER, event);
    }
}
