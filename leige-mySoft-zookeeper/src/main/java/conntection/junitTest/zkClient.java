package conntection.junitTest;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class zkClient {

    // 注意：逗号前后不能有空格
    private static String connectString = "192.168.0.130:2181,192.168.0.130:2182,192.168.0.130:2183";
    private static int sessionTimeout = 2000;
    private ZooKeeper zkClient = null;

    @Before
    public void init() throws Exception{
        zkClient = new ZooKeeper(connectString, sessionTimeout, new
                Watcher(){
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        // 收到事件通知后的回调函数（用户的业务逻辑）
                        //连接成功的时候会回调这个。这样就没有监听了，需要用下面的方法来注册
                        System.out.println(watchedEvent.getType() + "--"
                                + watchedEvent.getPath()+ "--"+watchedEvent.getState());

                        // 再次启动监听
                        try {
                            //当watch为true的时候，获取数据的同时会注册一个监听。这样在下次数据变化的时候，我们就能监听
                            //下次再执行这个的时候，还会注册监听。这样就会一直循环监听
                            List<String> children = zkClient.getChildren("/",
                                    true);
//                            for (String child : children) {
//                                System.out.println(child);
//                            }
                            System.out.println("hello");
                            children.stream().peek((e)-> System.out.println(e)).count();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }


    // 获取子节点
    @Test
    public void getChildren() throws Exception {
//        List<String> children = zkClient.getChildren("/", true);
//        for (String child : children) {
//            System.out.println(child);
//        }
        System.out.println("开始了");
        // 延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    // 创建子节点
    @Test
    public void create() throws Exception {
    // 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
        String nodeCreated = zkClient.create("/atguigu",
                "shuaige".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    @Test
    public void exist() throws Exception{
        Stat stat=zkClient.exists("/atguigu",false);
        System.out.println(stat);
    }

}
