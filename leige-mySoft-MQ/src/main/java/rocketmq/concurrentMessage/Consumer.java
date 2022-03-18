/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rocketmq.concurrentMessage;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.simple.MqConstant;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("gelei_retry_Concurrently");
        consumer.setNamesrvAddr(MqConstant.nameSrv);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //对于顺序消息，当消费者消费消息失败后，消息队列 RocketMQ 会自动不断进行消息重试（每次间隔时间为 1 秒），
        // 这时，应用会出现消息消费被阻塞的情况。因此，在使用顺序消息时，务必保证应用能够及时监控并处理消费失败的情况，
        // 避免阻塞现象的发生。并且这个消息和对应的消费者也不会进入系统的重试队列（对于顺序的消费者，不会创建重试队列）
        //broker的消费位点在出错的那消息的位置，一直不动。不断重试
        consumer.subscribe("TopicConcurrentlyRetry_2", "*");
        //和顺序的区别是，消费正常消费。位点正常往后移动，但是会进消费者对应的重队列
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //下面的方法是多线程去消费的，一参数List<MessageExt>里面只有一个消息(为什么是一个，因为每个消息都是要确认的，不可能出现批量确认的啊)
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s,body是：%s %n", Thread.currentThread().getName(), msgs,new String(msgs.get(0).getBody()));
                if((msgs.get(0).getQueueId()==2)&&(msgs.get(0).getQueueOffset()==0L)){
                    return null;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

//        这样是保证不了最终消费顺序的。
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                for(MessageExt msg:msgs){
//                    System.out.println("收到消息内容 "+new String(msg.getBody()));
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

}
