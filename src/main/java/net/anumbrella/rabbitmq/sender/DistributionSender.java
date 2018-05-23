package net.anumbrella.rabbitmq.sender;

import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributionSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(int i) {
		// 发送的消息
		String message = "This is a task, and the complexity is " + i + "。" + StringUtils.repeat(".", i);
		this.rabbitTemplate.convertAndSend("distribu", message);
	}

}
