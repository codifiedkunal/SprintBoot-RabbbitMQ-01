package com.kworld.bootrabbit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kworld.bootrabbit.entity.Message;
import com.kworld.bootrabbit.producer.RabbitMQProducer;
import com.kworld.bootrabbit.repository.MessageRepository;

@RestController
public class HelloController {

	@Autowired
	private RabbitMQProducer producer;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@GetMapping("/hello")
	public String sayHello(@RequestHeader String name) {
		Message message = new Message(UUID.randomUUID().toString(), "Hello " + name + " !!!");
		producer.sendMessage(message);
		return message.getMsg();
	}
	
	@GetMapping("/messages")
	public List<Message> fetchMessages(){
		return this.messageRepository.fetchMessages();
	}
}
