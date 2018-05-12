
package net.anumbrella.rabbitmq.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSender2 {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String msg) {
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sendMsg = msg + time.format(new Date()) + " hello2 ";
		System.out.println("Sender2 : " + sendMsg);
		this.rabbitTemplate.convertAndSend("hello", sendMsg);
	}

}