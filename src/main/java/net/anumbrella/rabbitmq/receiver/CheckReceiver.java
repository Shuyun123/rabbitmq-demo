package net.anumbrella.rabbitmq.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class CheckReceiver {

	@RabbitListener(queues = "hello")
	public void process(Message message, Channel channel) throws IOException {
		System.out.println("CheckReceiver: " + new String(message.getBody()));
		try {
			doWork();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 使用时需要在application.properties开启手动确认设置
		// 消息的标识，false只确认当前一个消息收到，true确认所有将比第一个参数指定的 delivery tag 小的consumer都获得的消息
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	private static void doWork() throws InterruptedException {
		Thread.sleep(1000);
	}
}
