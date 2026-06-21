package com.uade.order.infrastructure.adapter.in.messaging;

import com.uade.order.domain.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("rabbitmq")
public class ProductEventListener {

    private static final Logger log = LoggerFactory.getLogger(ProductEventListener.class);

    @RabbitListener(queues = "product-created-queue-order")
    public void handleProductCreated(ProductCreatedEvent event) {
        log.info("=== EVENTO PRODUCTO RECIBIDO EN ORDER-SERVICE ===");
        log.info("Producto ID: {}", event.getProductId());
        log.info("Nombre:      {}", event.getName());
        log.info("=================================================");
        // Aquí podríamos disparar la creación de una orden automática,
        // o guardar información del producto en nuestra base de datos local.
    }
}
