package rabbitmq.spring.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author
 */
@Component
public class AckListener implements ChannelAwareMessageListener {

    private static final int i=30;
    private static int count=0;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //1、获取消息的id
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {


        //2、获取消息
            if(count<i){
                System.out.println("message:"+new String(message.getBody()));
                count++;
            }

            if(count==i){
                System.exit(0);
            }


        //3、进行业务处理
        System.out.println("=====进行业务处理====");

        //模拟出现异常
//        int  i = 5/0;
            if(new String(message.getBody()).equals("message confirm....2")){
                int  i = 5/0;
            }

        //4、进行消息签收
        channel.basicAck(deliveryTag, true);

        } catch (Exception e) {

            //拒绝签收
             /*
            第三个参数：requeue：重回队列。如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端
            如果prefetch="1"，那个消息一直被消费失败，那么会阻塞消费。不断重复消费那个消息
             */
            channel.basicNack(deliveryTag, true, true);

        }
    }
}
