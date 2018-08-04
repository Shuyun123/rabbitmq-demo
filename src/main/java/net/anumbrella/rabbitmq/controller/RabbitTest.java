
package net.anumbrella.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.anumbrella.rabbitmq.sender.CallBackSender;
import net.anumbrella.rabbitmq.sender.DirectSender;
import net.anumbrella.rabbitmq.sender.DistributionSender;
import net.anumbrella.rabbitmq.sender.FanoutSender;
import net.anumbrella.rabbitmq.sender.HeadersSender;
import net.anumbrella.rabbitmq.sender.HelloSender1;
import net.anumbrella.rabbitmq.sender.HelloSender2;
import net.anumbrella.rabbitmq.sender.TopicSender;
import net.anumbrella.rabbitmq.sender.TransactionSender2;
import net.anumbrella.rabbitmq.sender.UserSender;

@RestController
@RequestMapping("/rabbit")
public class RabbitTest {

    @Autowired
    private HelloSender1 helloSender1;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private DirectSender directSender;

    @Autowired
    private HeadersSender headersSender;

    @Autowired
    private UserSender userSender;

    @Autowired
    private CallBackSender callBackSender;

    @Autowired
    private DistributionSender distributionSender;

    @Autowired
    private TransactionSender2 transactionSender;

    /**
     * 单生产者和单消费者
     */
    @GetMapping("/hello")
    public void hello() {
        helloSender1.send("hello1 ");
    }

    /**
     * 单生产者-多消费者
     */
    @GetMapping("/oneToMany")
    public void oneToMany() {
        for (int i = 0; i < 4; i++) {
            helloSender1.send("第[" + (i + 1) + "]个 ---------> ");
        }
    }

    /**
     * 多生产者-多消费者
     */
    @GetMapping("/manyToMany")
    public void manyToMany() {
        for (int i = 0; i < 4; i++) {
            helloSender1.send("第[" + (i + 1) + "]个 ---------> ");
            helloSender2.send("第[" + (i + 1) + "]个 ---------> ");
        }
    }

    /**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/topicTest")
    public void topicTest() {
        topicSender.send();
    }

    /**
     * fanout exchange类型rabbitmq测试
     */
    @GetMapping("/fanoutTest")
    public void fanoutTest() {
        fanoutSender.send();
    }

    /**
     * direct exchange类型rabbitmq测试
     */
    @GetMapping("/directTest")
    public void directTest() {
        directSender.send();
    }

    /**
     * headers exchange类型rabbitmq测试
     */
    @GetMapping("/headersTest")
    public void headersTest() {
        headersSender.send();
    }

    /**
     * 实体类传输测试
     */
    @GetMapping("/userTest")
    public void userTest() {
        userSender.send();
    }

    /**
     * 带callback的消息发送
     */
    @GetMapping("/callback")
    public void callbak() {
        callBackSender.send();
    }

    /**
     * 分发机制消息发送测试
     */
    @GetMapping("/distribu")
    public void distribu() {
        for (int i = 0; i < 5; i++) {
            //发送任务复杂度都为1的消息
            distributionSender.send(1);
        }
    }

    /**
     * 事务消息发送测试
     */
    @GetMapping("/transition")
    public void transition() {
        transactionSender.send("Transition:  ");
    }

}