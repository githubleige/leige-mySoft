package it.heima.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class FileChannelAccept {
    public static final String  GREETING  = "Hello java nio.\r\n";
    public static void main(String[] argv) throws Exception {
        int port = 30000;  // default
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        //设置为非阻塞的I/O
        ssc.configureBlocking(false);
        while (true) {
            System.out.println("Waiting for connections");
            //如果是阻塞式的I/O,这个线程再次会阻塞，直到有连接被创建
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("null");
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " +
                        sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
