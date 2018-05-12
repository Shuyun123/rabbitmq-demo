
package net.anumbrella.rabbitmq.receiver;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

public class TopicReceiver {

	@RabbitListener(queues = "topic.message")
    public void processA(String msg) {
        System.out.println("topicMessageReceiver  : " +msg);
    }
	
	@RabbitListener(queues = "topic.messages")
    public void processB(String msg) {
        System.out.println("topicMessagesReceiver  : " +msg);
    }
}