package rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import rabbitmq.utils.RabbitConstant;
import rabbitmq.utils.RabbitUtils;

import java.io.IOException;

/**
 * 生产者需要和exchange对应，投递消息指明exchange
 * 消费者需要和队列对应，消费消息只需要指明对应的消费队列
 * 至于队列和exchange如何绑定，那是管理者应该决定的
 * 队列和exchange可以是多对多的
 *
 */
public class QueueBind {
    public static void main(String[] args) throws IOException {

        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();
        //创建队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        //第一个参数：队列名称ID
        //第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
        //第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问((并且在该消费者断开连接就会删除该队列))
        //第四个：是否自动删除,false代表连接停掉后不自动删除掉这个队列
        //其他额外的参数, null
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);
        //queueBind用于将队列与交换机绑定
        //参数1：队列名 参数2：交互机名  参数三：路由key
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "*.*.*.20201127");
//        //指定队列与交换机以及routing key之间的关系
//        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "us.#");
    }
}
