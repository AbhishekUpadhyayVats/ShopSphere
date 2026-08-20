package com.lpu.email_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "email.queue";
    public static final String EXCHANGE = "email.exchange";
    public static final String ROUTING_KEY = "email.routingKey";

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

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}
}