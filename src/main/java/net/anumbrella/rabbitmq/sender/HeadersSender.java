
package net.anumbrella.rabbitmq.sender;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeadersSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {

		Map<String, Object> headers = new Hashtable<String, Object>();
		headers.put("name", "jack");
		headers.put("age", 30);
		String content = headers.toString();
		MessageProperties props = MessagePropertiesBuilder.newInstance()
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).setMessageId("123").setHeader("age", "30")
				.build();
		Message message = MessageBuilder.withBody(content.getBytes()).andProperties(props).build();

		System.out.println("sender1 : " + headers.toString());
		this.rabbitTemplate.convertAndSend("headersExchange", "", message);

	}

}
