package net.anumbrella.rabbitmq.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
/**
 * 这是java原生类支持RabbitMQ，直接运行该类
 */
public class DistributionSender3 {

	private final static String QUEUE_NAME = "test";

	public static void main(String[] args) throws IOException, TimeoutException {
		/**
		 * 创建连接连接到RabbitMQ
		 */
		ConnectionFactory factory = new ConnectionFactory();

		// 设置RabbitMQ所在主机ip或者主机名
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setHost("127.0.0.1");
		factory.setVirtualHost("/");
		factory.setPort(5672);

		// 创建一个连接
		Connection connection = factory.newConnection();

		// 创建一个频道
		Channel channel = connection.createChannel();

		// 指定一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		int prefetchCount = 1;
		// 限制发给同一个消费者不得超过1条消息
		channel.basicQos(prefetchCount);

		for (int i = 0; i < 8; i++) {
			// 发送的消息
			String message = "This is a task, and the complexity is " + 1 + "。" + StringUtils.repeat(".", 1);
			// 往队列中发出一条消息， 使用rabbitmq默认交换机
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

			System.out.println(" DistributionSender3 Sent '" + message + "'");
		}
		// 关闭频道和连接
		channel.close();
		connection.close();
	}

}
