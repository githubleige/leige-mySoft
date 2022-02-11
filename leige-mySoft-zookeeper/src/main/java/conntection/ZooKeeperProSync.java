package conntection;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZooKeeperProSync implements Watcher {
    //后面的参数设置为1，目前一个异步线程
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    //这个参数用来承装结点信息，其实就是就是复制返回的数据到这个对象中去
    private static Stat stat = new Stat();
    public static void main(String[] args) throws Exception{
        //zookeeper配置数据存放路径
        String path = "/mydata";
        //连接zookeeper并且注册一个默认的监听器
        zk = new ZooKeeper("192.168.106.163:2181", 5000, new ZooKeeperProSync());
        connectedSemaphore.await();
        System.out.println(new String(zk.getData(path, true, stat)));
        System.out.println("主方法的线程"+Thread.currentThread().getName());
        System.out.println("主方法线程是否是后台线程"+Thread.currentThread().isDaemon());
        //这里这样设置的原因是：监听线程是一个后台线程，如果主方法线程（唯一的前台线程）结束，会导致后台线程（监听线程）也结束
        Thread.sleep(Integer.MAX_VALUE);
    }

    //监听的原理就是网络通信（socket编程），监听线程和上面的主方法线程不是同一个线程
    public void process(WatchedEvent event){
        System.out.println("监听的线程"+Thread.currentThread().getName());
        System.out.println("监听的线程是否是后台线程"+Thread.currentThread().isDaemon());
        System.out.println("测试1");
        if (KeeperState.SyncConnected == event.getState()) { //zk连接成功通知事件
            if (EventType.None == event.getType() && null == event.getPath()) {
                System.out.println("测试2");
                connectedSemaphore.countDown();
            }
            else if (event.getType() == EventType.NodeDataChanged) { //zk目录节点数据变化通知事件
                try {
                    //zookeeper的监听是一次性的，使用过一次后就没有了。所以这里获取数据后，又重新监听了
                    //zk.getData(event.getPath(), true, stat)
                    System.out.println("配置已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
                } catch (Exception e) {

                }
            }
        }
    }
}
