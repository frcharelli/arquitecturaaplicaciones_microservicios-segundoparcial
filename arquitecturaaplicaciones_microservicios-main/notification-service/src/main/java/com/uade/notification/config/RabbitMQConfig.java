package com.uade.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("rabbitmq")
public class RabbitMQConfig {

    public static final String EXCHANGE = "inventory.exchange";
    public static final String QUEUE = "product.created.queue";
    public static final String ROUTING_KEY = "product.created";

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue productCreatedQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding productCreatedBinding(Queue productCreatedQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(productCreatedQueue).to(inventoryExchange).with(ROUTING_KEY);
    }
    
    @Bean
    public TopicExchange ecosystemExchange() {
        return new TopicExchange("ecosystem-exchange");
    }

    @Bean
    public Queue orderCreatedQueueNotification() {
        return QueueBuilder.durable("order-created-queue-notification").build();
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueueNotification, TopicExchange ecosystemExchange) {
        return BindingBuilder.bind(orderCreatedQueueNotification).to(ecosystemExchange).with("order.created");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
