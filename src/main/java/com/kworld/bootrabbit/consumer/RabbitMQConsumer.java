package com.kworld.bootrabbit.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kworld.bootrabbit.entity.Message;
import com.kworld.bootrabbit.repository.MessageRepository;

@Component
public class RabbitMQConsumer {
	private static final Logger log = LoggerFactory.getLogger(RabbitMQConsumer.class);

	@Autowired
	private	MessageRepository messageRepository;
	
	@RabbitListener(queues = "${helloworld.queue1.name:helloWorldQ1}")
    public void recieveMessage(final Message message) {
		try {
			if(message != null) {
				log.info("{} ---- {} ", message.getUuid(), message.getMsg());
				this.messageRepository.insert(message);
			} else {
				log.info(" ----- Null Message --- ");
			}
		} catch (Exception e) {
			log.error("-------Exception while reading message------", e);
			throw e;
		}
	}
}
