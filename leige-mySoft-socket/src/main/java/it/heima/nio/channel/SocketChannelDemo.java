package it.heima.nio.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class SocketChannelDemo {

    public static void main(String[] args) throws Exception {
        //创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));

//        SocketChannel socketChanne2 = SocketChannel.open();
//        socketChanne2.connect(new InetSocketAddress("www.baidu.com", 80));

        //设置阻塞和非阻塞（阻塞和非阻塞的区别就是在下面的socketChannel.read(byteBuffer);代码在服务端未写入数据的时候是否会导致线程阻塞）
        socketChannel.configureBlocking(false);

        //读操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        System.out.println(byteToChar(byteBuffer));
        socketChannel.close();
        System.out.println("read over");

    }

    private static CharBuffer byteToChar(ByteBuffer byteBuffer) throws CharacterCodingException {
        byteBuffer.flip();
        Charset charset= Charset.forName("GBK");
        CharsetDecoder decoder=charset.newDecoder();
        CharBuffer charBuffer=decoder.decode(byteBuffer);
        return charBuffer;
    }

}
