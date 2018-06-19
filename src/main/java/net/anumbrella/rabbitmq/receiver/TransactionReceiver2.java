package net.anumbrella.rabbitmq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class TransactionReceiver2 {

	@RabbitListener(queues = "transition")
	public void process(Message message, Channel channel) throws IOException {
		System.out.println("TransactionReceiver2  : " + new String(message.getBody()));
	}

}
