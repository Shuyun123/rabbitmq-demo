package net.anumbrella.rabbitmq.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DistributionReceiver {

	/**
	 * 消费者A
	 * 
	 * @param message
	 */
	@RabbitListener(queues = "distribu")
	public void processA(Message message) {
		String msg = new String(message.getBody());
		System.out.println(" DistributionReceiverA  : " + msg);
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
		System.out.println(" ProccessingA... at " + time.format(new Date()));

		try {
			for (char ch : msg.toCharArray()) {
				if (ch == '.') {
					doWork(1000);
				}
			}
		} catch (InterruptedException e) {
		} finally {
			System.out.println(" A Done! at " + time.format(new Date()));
		}
	}
	
	/**
	 * 消费者B
	 * 
	 * @param message
	 */
	@RabbitListener(queues = "distribu")
	public void processB(Message message) {
		String msg = new String(message.getBody());
		System.out.println(" DistributionReceiverB  : " + msg);
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
		System.out.println(" ProccessingB... at " + time.format(new Date()));

		try {
			for (char ch : msg.toCharArray()) {
				if (ch == '.') {
					doWork(1000);
				}
			}
		} catch (InterruptedException e) {
		} finally {
			System.out.println(" B Done! at " + time.format(new Date()));
		}
	}

	private void doWork(long time) throws InterruptedException {
		Thread.sleep(time);
	}

}
