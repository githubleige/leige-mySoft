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
package rocketmq.broadcast;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import rocketmq.simple.MqConstant;

import java.io.UnsupportedEncodingException;

public class Producer {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("gelei_producer");
            producer.setNamesrvAddr(MqConstant.nameSrv);
            producer.start();

//            for (int i = 0; i < 10; i++) {
                int orderId = 0;

//                for(int j = 0 ; j <= 5 ; j ++){
            int j = 0;
                    Message msg =
                            new Message("broadCastTopic", "order_"+orderId, "KEY" + orderId,
                                    ("order_"+orderId+" step " + j).getBytes(RemotingHelper.DEFAULT_CHARSET));

                    SendResult sendResult = producer.send(msg);


                    /*SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                        *//**
                         *
                         * @param mqs 对应主题创建的队列数目，一般在配置文件中，这里是8
                         * @param msg
                         * @param arg 这个参数是从外面的orderId传过来的
                         * @return
                         *//*
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            //因为同一个订单的orderid相同，所以取模也是相同的，保证了同一个订单的多个步骤都是落在一个主题的同一个队列上。
                            Integer id = (Integer) arg;
                            int index = id % mqs.size();
                            //这里明确的给出要发送到topic下具体哪一个队列。在同一个队列中是可以保证有序性的
                            return mqs.get(index);
                        }
                    }, orderId);*/

                    System.out.printf("%s%n", sendResult);
//                }
//            }

            producer.shutdown();
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}