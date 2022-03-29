package rabbitmq.spring.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq/producer/spring-rabbitmq-producer.xml")
public class ProducerTest {
    //其实在rabbitTemplate操作时候配置文件（xml）只需要配置connection-factory。因为我只需要连接端口建立网络连接，把数据发送给你
    //就可以了，具体你有没有相应的交换机和队列。是你服务端自己需要维护的事。这里在xml文件中配置，只是预防没有，我把需要创建的元信息告诉你
    @Autowired
    private RabbitTemplate  rabbitTemplate;


        //测试   Confirm 模式
    @Test
    public void testConfirm() {

         //定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 相关配置信息
             * @param ack   exchange交换机 是否成功收到了消息。true 成功，false代表失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm方法被执行了....");
                System.out.println("correlationData"+correlationData);

                 //ack 为  true表示 消息已经到达交换机
                if (ack) {
                    //接收成功
                    System.out.println(Thread.currentThread().getName()+"接收成功消息" + cause);
                    if(correlationData==null){
                        System.out.println("成功接收的消息的内容是：null");
                    }else{
                        System.out.println("成功接收的消息的内容是："+correlationData);
                    }

                } else {
                    //接收失败
                    System.out.println(Thread.currentThread().getName()+"接收失败消息" + cause);
                    //做一些处理，让消息再次发送。
                    if(correlationData==null){
                        System.out.println("接收失败消息：null");
                    }else{
                        System.out.println("接收的消息内容是："+correlationData);
                    }

                }
            }
        });

        //进行消息发送
//        rabbitTemplate.convertAndSend("test_exchange_confirm1","confirm"
//                ,"message Confirm...",new CorrelationData("1"));
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm123"
                ,"message Confirm...");

        //进行睡眠操作
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //测试 return模式
    @Test
    public void testReturn() {

        //设置交换机处理失败消息的模式   为true的时候，消息达到不了队列时，会将消息重新返回给生产者
        rabbitTemplate.setMandatory(true);

        //定义回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message   消息对象
             * @param replyCode 错误码
             * @param replyText 错误信息
             * @param exchange  交换机
             * @param routingKey 路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行了....");

                System.out.println("message:"+message);
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);

                //处理
            }
        });
        //进行消息发送
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm166","message return...");

        //进行睡眠操作
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     //批量发送消息，让消费者每次拉去指定的数量
     @Test
     public void  testQos(){

         for (int i = 0; i < 10; i++) {
             // 发送消息
             rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.gelei", "message confirm...."+i);
         }

     }



}
