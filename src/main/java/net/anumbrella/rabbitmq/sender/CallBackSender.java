
package net.anumbrella.rabbitmq.sender;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CallBackSender implements RabbitTemplate.ConfirmCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send() {

		rabbitTemplate.setConfirmCallback(this);
		String msg = "callbackSender : i am callback sender";
		System.out.println(msg);
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		System.out.println("callbackSender UUID: " + correlationData.getId());
		this.rabbitTemplate.convertAndSend("", "hello", msg, correlationData);
	}

	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		// 这里的ack是Broker对发布者消息达到服务端的确认
		System.out.println("callbakck confirm: " + correlationData.getId() + " ACK : " + ack + " cause : "+ cause);
	}
}