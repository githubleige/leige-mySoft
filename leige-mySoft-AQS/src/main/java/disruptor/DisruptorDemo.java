package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import disruptor.consumer.OrderEventHandler;
import disruptor.event.OrderEvent;
import disruptor.producer.OrderEventProducer;

import java.util.concurrent.Executors;

/**
 * 数组长度2^n，通过位运算，加快定位的速度。下标采取递增的形式。不用担心index溢出的
 * 问题。index是long类型，即使100万QPS的处理速度，也需要30万年才能用完。
 */
public class DisruptorDemo {

    public static void main(String[] args) throws Exception {

        //创建disruptor
        //创建空消息载体的工厂：OrderEvent::new等价于new OrderEventFactory()
        //这里需要线程工厂的作用就是创建消费者线程。生产者线程在应用程序中去启动的
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                OrderEvent::new,
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE, //单生产者
                new YieldingWaitStrategy()  //等待策略
        );

        //设置消费者用于处理RingBuffer的事件
        //disruptor.handleEventsWith(new OrderEventHandler());
        //设置多消费者,消费者要实现EventHandler接口,消息会被不同大的消费者重复消费
        disruptor.handleEventsWith(new OrderEventHandler(),new OrderEventHandler());
        //设置多消费者,消费者要实现WorkHandler接口，一条消息只会被一个消费者消费
//        disruptor.handleEventsWithWorkerPool(new OrderEventHandler(), new OrderEventHandler());

        //启动disruptor
        disruptor.start();

        //创建ringbuffer容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();
        //创建生产者
        OrderEventProducer eventProducer = new OrderEventProducer(ringBuffer);
        // 发送消息
        for(int i=0;i<100;i++){
            eventProducer.onData(i,"Fox"+i);
        }

        disruptor.shutdown();

    }
}
