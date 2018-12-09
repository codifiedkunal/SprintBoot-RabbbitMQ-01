package com.kworld.bootrabbit.config;

import java.net.URI;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	
	@Value("${AMQP_URI:amqp://guest:guest@localhost:5672/helloWorld}")
	private String amqpUri;
	
	@Value("${helloworld.queue1.name:helloWorldQ1}")
	private String queue1Name;

	@Value("${helloworld.exchange.name:helloWorldExchange}")
	private String exchangeName;
	
	@Value("${helloworld.exchange.routingKey:helloWorldQ1}")
	private String routingKey;
	
	@Bean
	public Queue queue1() {
		return new Queue(this.queue1Name, true);
	}
	
	@Bean
	public Exchange exchange() {
		return new DirectExchange(this.exchangeName, true, false);
	}
	
	@Bean
	public Binding bind(final Queue queue1, final DirectExchange exchange) {
		return BindingBuilder.bind(queue1).to(exchange).with(this.routingKey);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new	CachingConnectionFactory(URI.create(this.amqpUri));
		cachingConnectionFactory.setRequestedHeartBeat(10);
		return cachingConnectionFactory;
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
