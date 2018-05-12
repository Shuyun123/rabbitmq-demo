
package net.anumbrella.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.anumbrella.rabbitmq.sender.CallBackSender;
import net.anumbrella.rabbitmq.sender.DirectSender;
import net.anumbrella.rabbitmq.sender.FanoutSender;
import net.anumbrella.rabbitmq.sender.HeadersSender;
import net.anumbrella.rabbitmq.sender.HelloSender1;
import net.anumbrella.rabbitmq.sender.HelloSender2;
import net.anumbrella.rabbitmq.sender.TopicSender;
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

}