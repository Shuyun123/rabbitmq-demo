package net.anumbrella.rabbitmq.sender;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * 这是java原生类支持RabbitMQ，直接运行该类
 */
public class ConfirmSender2 {

    private final static String QUEUE_NAME = "confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        /**
         * 创建连接连接到RabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();

        // 设置MabbitMQ所在主机ip或者主机名
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
        // 发送的消息
        String message = "This is a confirm message！";

        channel.confirmSelect();
        final long start = System.currentTimeMillis();
        //发送持久化消息

        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
                if (multiple) {
                    confirmSet.headSet(deliveryTag + 1L).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
                if (multiple) {
                    confirmSet.headSet(deliveryTag + 1L).clear();
                } else {
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        for (int i = 0; i < 5; i++) {
            //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
            //因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
            //我们需要将该参数设置成创建的exchange的名字),第二个参数是路由键
            long nextSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, (" Confirm模式， 第" + (i + 1) + "条消息").getBytes());
            confirmSet.add(nextSeqNo);
        }


        System.out.println("执行异步耗费时间: " + (System.currentTimeMillis() - start) + "ms");


        // 关闭频道和连接
        //channel.close();
        //connection.close();

    }

}
