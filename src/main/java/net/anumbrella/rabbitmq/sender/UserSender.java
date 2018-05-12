
package net.anumbrella.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.anumbrella.rabbitmq.entity.User;

@Component
public class UserSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		User user = new User();
		user.setName("anumbrella");
		user.setAddress("China");
		System.out.println("user send : " + user.getName() + "/" + user.getAddress());
		this.rabbitTemplate.convertAndSend("user", user);
	}

}
