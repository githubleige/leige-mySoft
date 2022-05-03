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
package rocketmq.ordermessage;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import rocketmq.simple.MqConstant;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("gelei_retry");
        consumer.setNamesrvAddr(MqConstant.nameSrv);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //对于顺序消息，当消费者消费消息失败后，消息队列 RocketMQ 会自动不断进行消息重试（每次间隔时间为 1 秒），
        // 这时，应用会出现消息消费被阻塞的情况。因此，在使用顺序消息时，务必保证应用能够及时监控并处理消费失败的情况，
        // 避免阻塞现象的发生。并且这个失败的消息也不会进入系统的重试队列（虽然对该消费者来说会创建重试队列）
        //broker的消费位点在出错的那消息的位置，一直不动。不断重试
        consumer.subscribe("TopicOrderlyRetry", "*");

//        consumer.setMaxReconsumeTimes(20);
        //有序性是在线程级别保证的同时也在进程级别保证（一个消费者中的一个线程会对应消费主题下的一个队列，即：主题下的一个队列只能对应一个进程ID）
        //防止夸进程和线程造成线程不安全问题（有序性在一个队列,z在这个进程内部是可以启用多线程来处理一个订单的，但是步骤之间通过线程通信来保持同步通信）
        //用一个进程去绑定一个队列
        consumer.registerMessageListener(new MessageListenerOrderly() {
            //因为用这种方式保证了在同一个队列中的信息会被顺序消费，但是在不同队列之间消息消费是没有顺序的
            //这就是所说的：局部（处于同一个队列中的消息）有序，整体（处于不同队列但是被用一个消费者消费的信息）无序
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
//                for(MessageExt msg:msgs){
//                    System.out.printf("%s 收到消息内容: %s %n ",Thread.currentThread().getName(),msg);
//                }
                System.out.printf("%s 收到消息内容: %s,body是：%s %n ",Thread.currentThread().getName(),msgs,new String(msgs.get(0).getBody()));
                if((msgs.get(0).getQueueId()==2)&&(msgs.get(0).getQueueOffset()==2L)){
                    return null;
                }
                return ConsumeOrderlyStatus.SUCCESS;
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
