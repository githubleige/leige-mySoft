package it.heima.nio.buffer;


import org.junit.Test;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class BufferDemo1 {

    @Test
    public void buffer01() throws Exception {
        String path="D:\\FTPShare\\a.java";
        //FileChannel
        RandomAccessFile aFile =
                new RandomAccessFile(path,"rw");
        FileChannel channel = aFile.getChannel();

        //创建buffer，大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读
        int bytesRead = channel.read(buffer);

        while(bytesRead != -1) {
            //read模式，在从buffer中读取数据需要调用这个方法
            buffer.flip();

            while(buffer.hasRemaining()) {
                //如果对应文件里里面有中文的话，下面的会出现乱码
                System.out.println((char)buffer.get());
            }
            //在往buffer中写入数据时候需要调用这个方法
            buffer.clear();
            bytesRead = channel.read(buffer);
        }

        aFile.close();
    }


    @Test
    public void buffer02() throws Exception {

//        //创建buffer
//        IntBuffer buffer = IntBuffer.allocate(8);
//
//        //buffer放
//        for (int i = 0; i < buffer.capacity(); i++) {
//            int j = 2*(i+1);
//            buffer.put(j);
//        }
//
//        //重置缓冲区
//        buffer.flip();
//
//        //获取
//        while(buffer.hasRemaining()) {
//            int value = buffer.get();
//            System.out.println(value+" ");
//        }

        // 1、获取Selector选择器
        Selector selector = Selector.open();

        // 2、获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 3.设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 4、绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9999));

        // 5、将通道注册到选择器上,并制定监听事件为：“接收”事件
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

    }

}
