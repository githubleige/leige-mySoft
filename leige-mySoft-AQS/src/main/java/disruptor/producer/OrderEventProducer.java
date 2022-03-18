package disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import disruptor.event.OrderEvent;

/**
 *
 *
 * 消息（事件）生产者
 * 生产者每添加一个元素到环形队列中去，都相当于出发一个事件，事件的监听者是消费者
 */
public class OrderEventProducer {
    //事件队列
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    //向队列中发送数据的方法
    public void onData(long value,String name) {
        // 获取事件队列 的下一个槽
        long sequence = ringBuffer.next();
        try {
            //获取消息（事件）这是一个空消息体，需要填充
            OrderEvent orderEvent = ringBuffer.get(sequence);
            // 写入消息数据
            orderEvent.setValue(value);
            orderEvent.setName(name);
        } catch (Exception e) {
            // TODO  异常处理
            e.printStackTrace();
        } finally {
            System.out.println("生产者"+ Thread.currentThread().getName()
                    +"发送数据value:"+value+",name:"+name);
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
