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
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package rocketmq.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import rocketmq.simple.MqConstant;

import java.util.ArrayList;
import java.util.List;

public class SingleProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("singleProducerGroupName");
        producer.setNamesrvAddr(MqConstant.nameSrv);
        producer.start();

        //If you just send messages of no more than 1MiB at a time, it is easy to use single
        //Messages of the same single should have: same topic, same waitStoreMsgOK and no schedule support
        String topic = "singleTest";
        List<Message> messages = new ArrayList<>();
        /*messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));*/
        String keys,body;
        //这个批量的消息在打包成一个数据包发送的时候会会往主题里面的一个队列发送。即使里面包含若干条消息
        for (int i=0;i<100;i++){
            keys="OrderID"+i;
            body="Hello world "+i;
            messages.add(new Message(topic, "Tag", keys, body.getBytes()));
        }
        producer.send(messages);
        producer.shutdown();
    }
}
