package com.uade.order.infrastructure.adapter.out.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "ecosystem-exchange";
    public static final String QUEUE_ORDER_CREATED = "order-created-queue";
    public static final String ROUTING_KEY_ORDER = "order.created";
    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(QUEUE_ORDER_CREATED, true);
    }
    
    @Bean
    public Binding bindingOrder(Queue orderCreatedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(exchange).with(ROUTING_KEY_ORDER);
    }
    
    @Bean
    public Queue productCreatedQueueForOrder() {
        return new Queue("product-created-queue-order", true);
    }
    
    @Bean
    public Binding bindingProductForOrder(Queue productCreatedQueueForOrder, TopicExchange exchange) {
        return BindingBuilder.bind(productCreatedQueueForOrder).to(exchange).with("product.created");
    }
}
