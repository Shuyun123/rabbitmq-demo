package net.anumbrella.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    // ===============以下是验证rabbitmq默认 Exchange的队列和交互机==========

    /**
     * 申明hello队列
     *
     * @return
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    /**
     * 申明user队列
     *
     * @return
     */
    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    /**
     * 申明distribu队列
     *
     * @return
     */
    @Bean
    public Queue DistribuQueue() {
        return new Queue("distribu");
    }

    // ===============以上是验证rabbitmq默认 Exchange的队列和交互机==========

    // ===============以下是验证topic Exchange的队列和交互机==========

    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     *
     * @param queueMessage
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param queueMessages
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
    }

    // ===============以上是验证topic Exchange的队列和交互机==========

    // ===============以下是验证Fanout Exchange的队列和交互机==========

    @Bean
    public Queue fanoutQueueA() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue fanoutQueueB() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue fanoutQueueC() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue fanoutQueueA, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue fanoutQueueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue fanoutQueueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueueC).to(fanoutExchange);
    }

    // ===============以上是验证Fanout Exchange的队列和交互机==========

    // ===============以下是验证Direct Exchange的队列和交互机==========
    @Bean
    public Queue directQueueA() {
        return new Queue("direct.A");
    }

    @Bean
    public Queue directQueueB() {
        return new Queue("direct.B");
    }

    @Bean
    public Queue directQueueC() {
        return new Queue("direct.C");
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    Binding bindingDirectExchangeA(Queue directQueueA, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueA).to(directExchange).with("direct.a");
    }

    @Bean
    Binding bindingDirectExchangeB(Queue directQueueB, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueB).to(directExchange).with("direct.b");
    }

    @Bean
    Binding bindingDirectExchangeC(Queue directQueueC, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueC).to(directExchange).with("direct.c");
    }

    // ===============以上是验证Direct Exchange的队列和交互机==========

    // ===============以下是验证Headers Exchange的队列和交互机==========

    @Bean
    public Queue headersQueueA() {
        return new Queue("headers.A");
    }

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("headersExchange");
    }

    @Bean
    Binding bindingHeadersExchangeA(Queue headersQueueA, HeadersExchange headersExchange) {
        // 这里x-match有两种类型
        // all:表示所有的键值对都匹配才能接受到消息
        // any:表示只要有键值对匹配就能接受到消息
        return BindingBuilder.bind(headersQueueA).to(headersExchange).where("age").exists();
    }

    // ===============以上是验证Headers Exchange的队列和交互机==========

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses + ":" + port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        /** 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }

    /**
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplateNew() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        // 使用Confirm模式，关闭掉事务
        // template.setChannelTransacted(true);
        return template;
    }

    /**
     * 声明transition队列，防止直接启动报错
     *
     * @return
     */
    @Bean
    public Queue transitionQueueA() {
        return new Queue("transition");
    }



    /**
     * 声明transition2队列
     *
     * @return
     */
    @Bean
    public Queue transitionQueue() {
        return new Queue("transition2");
    }

    /**
     * Spring Boot开启事务(异步模式)
     * 使用Confirm模式时，需要注释掉
     *
     * @return
     */
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setTransactionManager(rabbitTransactionManager());
//        container.setChannelTransacted(true);
//        // 开启手动确认
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setQueues(transitionQueue());
//        container.setMessageListener(new TransitionConsumer());
//        return container;
//    }


    /**
     * 事务管理
     *
     * @return
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager() {
        return new RabbitTransactionManager(connectionFactory());
    }

    /**
     * 自定义消费者
     */
    public class TransitionConsumer implements ChannelAwareMessageListener {

        @Override
        public void onMessage(Message message, Channel channel) throws Exception {
            byte[] body = message.getBody();
            System.out.println("TransitionConsumer: " + new String(body));
            // 确认消息成功消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 除以0，模拟异常，进行事务回滚
            // int t = 1 / 0;
        }
    }

}
