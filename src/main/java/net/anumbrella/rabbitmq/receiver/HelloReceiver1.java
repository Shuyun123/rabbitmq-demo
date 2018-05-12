
package net.anumbrella.rabbitmq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class HelloReceiver1 {

	@RabbitListener(queues = "hello")
	public void process(Message message, Channel channel) throws IOException {
		System.out.println("Receiver1  : " + new String(message.getBody()));

		// true 发送给下一个消费者
		// false 谁都不接受，从队列中删除
		// 拒绝消息，RabbitMQ把消息发送给下一个监听hello的队列(HelloReceiver2或CheckReceiver)
		channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); 
	}

}
