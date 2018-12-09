package com.kworld.bootrabbit.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kworld.bootrabbit.entity.Message;

@Component
public class RabbitMQProducer {
	private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

	@Value("${helloworld.exchange.name:helloWorldExchange}")
	private String exchangeName;
	
	@Value("${helloworld.exchange.routingKey:helloWorldQ1}")
	private String routingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(Message message) {
		try {
			rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
		} catch (AmqpException e) {
			log.error("failed to send message to rabbitmq", e);
		}
	}
}
