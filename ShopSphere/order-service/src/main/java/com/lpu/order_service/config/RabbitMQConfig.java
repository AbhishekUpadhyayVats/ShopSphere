package com.lpu.order_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "email.queue";
    public static final String EXCHANGE = "email.exchange";
    public static final String ROUTING_KEY = "email.routingKey";
    
//    public static final String PAYMENT_QUEUE = "order.payment.queue";
//    public static final String PAYMENT_EXCHANGE = "order.exchange";
//    public static final String PAYMENT_ROUTING_KEY = "order.routingkey";


    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }
    
//    @Bean
//    public Queue paymentQueue() {
//        return new Queue(PAYMENT_QUEUE);
//    }
//
//    @Bean
//    public TopicExchange paymentExchange() {
//        return new TopicExchange(PAYMENT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding paymentBinding() {
//        return BindingBuilder
//                .bind(paymentQueue())
//                .to(paymentExchange())
//                .with(PAYMENT_ROUTING_KEY);
//    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}