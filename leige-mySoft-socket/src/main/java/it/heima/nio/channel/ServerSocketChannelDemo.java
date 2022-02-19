package it.heima.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //端口号
        int port = 8888;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello atguigu".getBytes());

        //ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式（阻塞和非阻塞的区别就是下面的SocketChannel sc = ssc.accept();在没有新的链接建立的时候是否会导致线程阻塞）
        ssc.configureBlocking(false);

        //监听有新链接传入
        while(true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if(sc == null) { //没有链接传入
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
                buffer.rewind(); //指针0
//                Thread.sleep(10000);
                //这行代码可能会导致在客户端socketChannel.read的代码不再阻塞
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
