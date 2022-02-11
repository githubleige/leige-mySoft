package zkcase1;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class DistributeServer {
    private static String connectString =
            "192.168.106.103:2181,192.168.106.103:2182,192.168.106.103:2183";
    private static int sessionTimeout = 2000;
    private ZooKeeper zk = null;
    private String parentNode = "/servers";
    // 创建到 zk 的客户端连接
    public void getConnect() throws IOException{
        zk = new ZooKeeper(connectString, sessionTimeout, new
                Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                    }
                });
        //使用lambda表达式
//        zk=new ZooKeeper(connectString, sessionTimeout,event->{});
    }
    // 注册服务器
    public void registServer(String hostname) throws Exception{
        String create = zk.create(parentNode + "/server",
                hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname +" is online "+ create);
    }
    // 业务功能
    public void business(String hostname) throws Exception{
        System.out.println(hostname + " is working ...");
        Thread.sleep(Long.MAX_VALUE);
    }
    public static void main(String[] args) throws Exception {
    // 1 获取 zk 连接
        DistributeServer server = new DistributeServer();
        server.getConnect();
    // 2 利用 zk 连接注册服务器信息
        server.registServer(args[0]);
    // 3 启动业务功能
        server.business(args[0]);
    }
}