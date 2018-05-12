
package net.anumbrella.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class FanoutReceiver {

	@RabbitListener(queues = "fanout.A")
	public void processA(String msg) {
		System.out.println("FanoutReceiverA  : " + msg);
	}

	@RabbitListener(queues = "fanout.B")
	public void processB(String msg) {
		System.out.println("FanoutReceiverB  : " + msg);
	}

	@RabbitListener(queues = "fanout.C")
	public void processC(String msg) {
		System.out.println("FanoutReceiverC  : " + msg);
	}

}