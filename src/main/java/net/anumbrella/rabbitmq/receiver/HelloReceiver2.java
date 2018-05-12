
package net.anumbrella.rabbitmq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class HelloReceiver2 {

	@RabbitListener(queues = "hello")
	public void process(Message message, Channel channel) throws IOException {
		System.out.println("Receiver2 : " + new String(message.getBody()));

	}

}
