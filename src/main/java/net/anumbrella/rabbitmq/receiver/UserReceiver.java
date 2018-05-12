
package net.anumbrella.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import net.anumbrella.rabbitmq.entity.User;

@Component

public class UserReceiver {

	@RabbitListener(queues = "user")
	public void process(User user) {
		System.out.println("user receive  : " + user.getName() + " / " + user.getAddress());
	}

}
