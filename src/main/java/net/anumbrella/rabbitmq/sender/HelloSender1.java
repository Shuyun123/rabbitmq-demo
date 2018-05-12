
package net.anumbrella.rabbitmq.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender1 {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String msg) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sendMsg = msg + time.format(new Date()) + " hello1 ";
		System.out.println("Sender1 : " + sendMsg);
		this.rabbitTemplate.convertAndSend("hello", sendMsg);
	}

}