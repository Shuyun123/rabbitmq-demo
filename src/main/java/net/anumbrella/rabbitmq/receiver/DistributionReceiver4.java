package net.anumbrella.rabbitmq.receiver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 这是java原生类支持RabbitMQ，直接运行该类
 */
public class DistributionReceiver4 {

    private final static String QUEUE_NAME = "test";

    public static void main(String[] argv) throws IOException, InterruptedException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();


        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("127.0.0.1");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        // 打开连接和创建频道，与发送端一样

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("DistributionReceiver4 waiting for messages. To exit press CTRL+C");

        //保证一次只分发一个
        channel.basicQos(1);

        // 创建队列消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" DistributionReceiver4  : " + message);
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
                System.out.println(" Proccessing4... at " + time.format(new Date()));
                try {
                    for (char ch : message.toCharArray()) {
                        if (ch == '.') {
                            doWork(1000);
                        }
                    }
                } catch (InterruptedException e) {
                } finally {
                    System.out.println(" DistributionReceiver4 Done! at " + time.format(new Date()));
                    // 手动确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 关闭自动确认
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }


    private static void doWork(long time) throws InterruptedException {
        Thread.sleep(time);
    }

}
