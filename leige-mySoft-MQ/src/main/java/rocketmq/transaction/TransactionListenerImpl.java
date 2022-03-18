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
package rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    //会首先把事务消息发送到系统主题的RMQ_SYS_TRANS_HALF_TOPIC返回状态为：SEND_OK。那么会用相同的线程调用这个方法，
    // 不会用线程池的线程执行，这和主方法是同一个线程，同步的
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//        int value = transactionIndex.getAndIncrement();
//        int status = value % 3;
//        localTrans.put(msg.getTransactionId(), status);
//        return LocalTransactionState.UNKNOW;
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("executeLocalTransaction thread %s,arg:%s %n",Thread.currentThread(),arg);
        String tags = msg.getTags();
        if(StringUtils.contains(tags,"TagA")){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags,"TagB")){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }

    @Override
    //这个方法会用线程池的的线程来执行
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//        Integer status = localTrans.get(msg.getTransactionId());
//        if (null != status) {
//            switch (status) {
//                case 0:
//                    return LocalTransactionState.UNKNOW;
//                case 1:
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                case 2:
//                    return LocalTransactionState.ROLLBACK_MESSAGE;
//                default:
//                    return LocalTransactionState.COMMIT_MESSAGE;
//            }
//        }
//        return LocalTransactionState.COMMIT_MESSAGE;
        System.out.printf("checkLocalTransaction thread %s,msg:%s %n",Thread.currentThread(),msg);
        String tags = msg.getTags();
        if(StringUtils.contains(tags,"TagC")){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags,"TagD")){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }
}
