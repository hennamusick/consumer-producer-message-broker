package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableBinding(ProducerChannels.class)
public class ConsumerApplication {
		
	private final MessageChannel consumer;
	
	public ConsumerApplication(ProducerChannels	channels) {
		this.consumer = channels.consumer();
	}

	@PostMapping ("/greet/{name}")
	public void publish (@PathVariable String name) {
		String greeting = "Hello" + name + "!"; 
		Message<String> msg = MessageBuilder.withPayload(greeting).build();	
		this.consumer.send(msg);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}

interface ProducerChannels {
	
	@Output
	MessageChannel consumer();
}
